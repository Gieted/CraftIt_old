package org.craftit.runtime.bytecode_modifiers

import javassist.*
import org.craftit.runtime.source_maps.SourceMap
import java.security.ProtectionDomain
import org.craftit.runtime.bytecode_modifiers.BytecodeModifier.ClassScope as ClassScope1


abstract class BytecodeModifier(
    private val classPool: ClassPool,
    protected val sourceMap: SourceMap,
) {

    @Suppress("PropertyName")
    protected val Bridge = "org.craftit.runtime.Bridge"
    
    private val classesToLoad = mutableListOf<CtClass>()

    open class ClassScope<T : SourceMap.Class>(
        private val map: T,
        protected val theClass: CtClass,
        private val classPool: ClassPool,
    ) {
        fun withSourceMap(configure: T.() -> Unit) = map.configure()

        fun method(
            name: String,
            vararg parameterTypes: String,
            parametersCount: Int? = null,
            configure: CtMethod.() -> Unit
        ) {
            val method = (theClass.declaredMethods + theClass.methods).find { method ->
                method.name == name
                        && (if (parametersCount != null) method.parameterTypes.size == parametersCount else true)

                        && (if (parameterTypes.isNotEmpty()) parameterTypes.size <= method.parameterTypes.size
                        && method.parameterTypes.slice(parameterTypes.indices)
                        == parameterTypes.map { classPool.get(it) } else true)
            }!!

            val methodCopy = CtMethod(method, theClass, null)
            methodCopy.name = "${method.name}_native"

            theClass.addMethod(methodCopy)

            method.configure()
        }

        fun field(name: String, initializer: String) {
            val field = theClass.fields.find { it.name == name }!!

            theClass.classInitializer.insertAfter("""${field.name} = $initializer;""")
        }

        fun constructor(src: String) {
            val constructor = CtNewConstructor.make(src, theClass)
            theClass.addConstructor(constructor)
        }
    }

    class ClassCreationScope<T : SourceMap.Class>(
        map: T,
        theClass: CtClass,
        classPool: ClassPool,
        private val parent: CtClass
    ) : ClassScope<T>(map, theClass, classPool) {

        fun delegateConstructor(select: (CtConstructor) -> Boolean) {
            val parameterTypes = parent.constructors.find(select)!!.parameterTypes
            val parameters =
                parameterTypes
                    .foldIndexed("") { index, acc, param -> "$acc, ${param.name} _$index" }
                    .drop(2)


            val constructor = CtNewConstructor.make(
                """
                public ${theClass.name}($parameters) {
                    super(${parameterTypes.indices.joinToString("") { "_$it, " }.dropLast(2)});
                }
                """, theClass
            )
            theClass.addConstructor(constructor)
        }
    }

    fun <T : SourceMap.Class> modifyClass(select: SourceMap.() -> T, configure: ClassScope1<T>.(CtClass) -> Unit) {
        val modifiedClassMap = sourceMap.select()
        val modifiedClass = classPool.get(modifiedClassMap())
        val classScope = ClassScope1(modifiedClassMap, modifiedClass, classPool)
        classScope.configure(modifiedClass)
        
        classesToLoad.add(modifiedClass)
    }

    fun <T : SourceMap.Class> createClass(
        name: String,
        superClass: SourceMap.() -> T,
        configure: ClassCreationScope<T>.(CtClass) -> Unit
    ) {
        val extendedClassMap = sourceMap.superClass()
        val extendedClass = classPool.get(extendedClassMap())
        val createdClass = classPool.makeClass(name, extendedClass)
        val classCreationScope = ClassCreationScope(extendedClassMap, createdClass, classPool, extendedClass)
        classCreationScope.configure(createdClass)
        
        classesToLoad.add(createdClass)
    }

    abstract fun configure()
}
