package org.craftit.runtime.bytecode_modifiers

import javassist.ClassPool
import javassist.CtMethod
import org.craftit.runtime.source_maps.SourceMap
import java.security.ProtectionDomain


abstract class BytecodeModifier(
    private val classPool: ClassPool,
    protected val sourceMap: SourceMap,
    protected val classLoader: ClassLoader,
    private val protectionDomain: ProtectionDomain
) {

    @Suppress("PropertyName")
    protected val Bridge = "org.craftit.runtime.Bridge"

    fun SourceMap.Class.method(
        name: String,
        vararg parameterTypes: String,
        parametersCount: Int? = null,
        modify: CtMethod.() -> Unit
    ) {
        val modifiedClass = classPool.get(this())
        val method = modifiedClass.declaredMethods.find { method ->
            method.name == name
                    && (if (parametersCount != null) method.parameterTypes.size == parametersCount else true)

                    && (if (parameterTypes.isNotEmpty()) parameterTypes.size >= method.parameterTypes.size
                    && method.parameterTypes.slice(parameterTypes.indices)
                    == parameterTypes.map { classPool.get(it) } else true)
        }!!

        val methodCopy = CtMethod(method, modifiedClass, null)
        methodCopy.name = "${method.name}_native"

        modifiedClass.addMethod(methodCopy)

        method.modify()
    }

    fun <T : SourceMap.Class> modifyClass(select: SourceMap.() -> T, modify: T.() -> Unit) {
        val modifiedClassMap = sourceMap.select()
        val modifiedClass = classPool.get(modifiedClassMap())
        modifiedClassMap.modify()
        modifiedClass.toClass(classLoader, protectionDomain)
    }

    abstract fun modify()
}
