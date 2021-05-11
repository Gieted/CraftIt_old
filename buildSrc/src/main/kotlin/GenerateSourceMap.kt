import com.squareup.kotlinpoet.*
import org.gradle.api.DefaultTask
import org.gradle.api.provider.MapProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction
import java.io.File
import javax.annotation.Generated

abstract class GenerateSourceMap : DefaultTask() {
    private class Class(val name: String, val members: List<String>, val closestPackage: Package) {
        val reference: String
            get() = "${closestPackage.reference}.$name"
    }

    private class Package(
        val name: String,
        val children: MutableList<Package> = mutableListOf(),
        val classes: MutableList<Class> = mutableListOf(),
        val parent: Package?
    ) {
        val reference: String
            get() = if (parent?.parent != null) "${parent.reference}.$name" else name
    }

    @get:Input
    abstract val scheme: MapProperty<String, List<String>>

    @get:Input
    abstract val packageName: Property<String>

    @get:OutputDirectory
    val outputDirectory: File = project.buildDir.resolve("generated/source/kotlin/main")

    @TaskAction
    fun generate() {
        fun <T> MutableList<T>.findOrAdd(value: T, finder: (T) -> Boolean) = find(finder) ?: run {
            add(value)

            value
        }

        val packageName = packageName.get()
        val scheme = scheme.get()

        val rootPackage = Package("", parent = null)

        for ((identifier, members) in scheme.entries) {
            val parentPackages = identifier.split(".").dropLast(1)
            val closestPackage = parentPackages.fold(rootPackage) { acc, s ->
                acc.children.findOrAdd(Package(s, parent = acc)) { it.name == s }
            }

            val className = identifier.split(".").last()
            closestPackage.classes.add(Class(className, listOf("identifier") + members, closestPackage))
        }

        val sourceMap = TypeSpec.classBuilder("SourceMap").addModifiers(KModifier.DATA)
            .addType(
                TypeSpec.companionObjectBuilder().addFunction(
                    FunSpec.builder("create")
                        .addParameter(
                            "configure",
                            LambdaTypeName.get(ClassName("", "Builder"), returnType = UNIT)
                        ).returns(ClassName("", "SourceMap"))
                        .addStatement("val builder = Builder()")
                        .addStatement("builder.configure()")
                        .addStatement("return builder.build()")
                        .build()
                ).build()
            ).addType(
                TypeSpec.classBuilder("IncompleteSourceMapException")
                    .superclass(ClassName("kotlin", "Exception"))
                    .primaryConstructor(
                        FunSpec.constructorBuilder()
                            .addParameter("memberName", String::class)
                            .addParameter("classIdentifier", String::class)
                            .build()
                    )
                    .addSuperclassConstructorParameter("\"You must fill \$memberName in \$classIdentifier\"")
                    .build()
            ).addFunction(
                FunSpec.builder("invoke")
                    .addModifiers(KModifier.OPERATOR)
                    .addTypeVariable(TypeVariableName("T", ClassName("", "Class")))
                    .addParameter(
                        "browse",
                        LambdaTypeName.get(
                            ClassName("", "SourceMap"),
                            returnType = TypeVariableName("T")
                        )
                    )
                    .addStatement("return this.browse()")
                    .build()
            ).addType(
                TypeSpec.classBuilder("Class")
                    .primaryConstructor(FunSpec.constructorBuilder().addParameter("identifier", String::class).build())
                    .addProperty(PropertySpec.builder("identifier", String::class).initializer("identifier").build())
                    .addModifiers(KModifier.ABSTRACT)
                    .addFunction(
                        FunSpec.builder("invoke").addModifiers(KModifier.OPERATOR).addStatement("return identifier")
                            .build()
                    )
                    .build()
            ).addAnnotation(Generated::class)

        val sourceMapBuilder = TypeSpec.classBuilder("Builder")

        fun fillTypeSpec(parent: TypeSpec.Builder, parentBuilder: TypeSpec.Builder, packageDef: Package) {
            val constructorBuilder = FunSpec.constructorBuilder()
            for (childPackage in packageDef.children) {
                val className = childPackage.name.capitalize()
                constructorBuilder.addParameter(childPackage.name, ClassName("", className))
                parent.addProperty(
                    PropertySpec.builder(childPackage.name, ClassName("", className))
                        .initializer(childPackage.name)
                        .build()
                )

                parentBuilder.addProperty(
                    PropertySpec.builder(childPackage.name, ClassName("", className))
                        .initializer("$className()")
                        .build()
                )

                val typeSpec = TypeSpec.classBuilder(className).addModifiers(KModifier.DATA)
                val builderTypeSpec = TypeSpec.classBuilder(className)
                fillTypeSpec(typeSpec, builderTypeSpec, childPackage)
                parent.addType(typeSpec.build())
                parentBuilder.addType(builderTypeSpec.build())
            }

            for (childClass in packageDef.classes) {
                val className = childClass.name + "Class"

                constructorBuilder.addParameter(childClass.name, ClassName("", className))
                parent.addProperty(
                    PropertySpec.builder(childClass.name, ClassName("", className)).initializer(childClass.name).build()
                )
                parent.addType(
                    TypeSpec.classBuilder(className).also { typeSpec ->
                        childClass.members.drop(1).forEach {
                            typeSpec.addProperty(
                                PropertySpec.builder(it, String::class).initializer(it).build()
                            )
                        }
                    }.primaryConstructor(
                        FunSpec.constructorBuilder().also { constructor ->
                            childClass.members.forEach { constructor.addParameter(it, String::class) }
                        }.build()
                    ).superclass(ClassName("", "Class"))
                        .addSuperclassConstructorParameter("identifier")
                        .build()
                )

                parentBuilder.addType(TypeSpec.classBuilder(className).also { typeSpec ->
                    val invoke = FunSpec.builder("invoke").addModifiers(KModifier.OPERATOR)
                    childClass.members.forEach {
                        typeSpec.addProperty(
                            PropertySpec.builder(it, String::class.asTypeName().copy(nullable = true))
                                .initializer("null")
                                .mutable()
                                .build()
                        )

                        invoke.addParameter(it, String::class)
                        invoke.addStatement("this.$it = $it")
                    }

                    typeSpec.addFunction(invoke.build())

                    val builtType =
                        "SourceMap." + childClass.reference.split(".").joinToString(".") { it.capitalize() } + "Class"
                    typeSpec.addFunction(
                        FunSpec.builder("build")
                            .addStatement("return $builtType(${
                                childClass.members.joinToString("") { """$it ?: throw IncompleteSourceMapException("$it", "${childClass.reference}"), """ }
                            })").build()
                    )
                }.build())

                parentBuilder.addProperty(
                    PropertySpec.builder(childClass.name, ClassName("", className)).initializer("$className()").build()
                )
            }

            parent.primaryConstructor(constructorBuilder.build())

            val builtType =
                "SourceMap" + if (packageDef.parent == null) "" else "." + packageDef.reference.split(".")
                    .joinToString(".") { it.capitalize() }
            parentBuilder.addFunction(
                FunSpec.builder("build")
                    .addStatement(
                        "return $builtType(${
                            packageDef.children.joinToString("") { "${it.name}.build(), " }
                        }${
                            packageDef.classes.joinToString(", ") { "${it.name}.build()" }
                        })"
                    ).build()
            )
        }

        fillTypeSpec(sourceMap, sourceMapBuilder, rootPackage)

        val file = FileSpec.builder(packageName, "SourceMap")
            .addType(sourceMap.addType(sourceMapBuilder.build()).build())

        file.build().writeTo(outputDirectory)
    }
}
