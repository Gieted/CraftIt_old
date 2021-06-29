package org.craftit.runtime.resources.commands

import com.mojang.brigadier.arguments.ArgumentType
import org.craftit.runtime.source_maps.SourceMap
import java.lang.reflect.Method
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
class EntityArgumentWrapper @Inject constructor(sourceMap: SourceMap, classLoader: ClassLoader) {
    private val entityMethod: Method by lazy {
        with(sourceMap { net.minecraft.command.arguments.EntityArgument }) {
            val entityArgumentClass = classLoader.loadClass(this())
            entityArgumentClass.getDeclaredMethod(entity)
        }
    }

    private val entitiesMethod: Method by lazy {
        with(sourceMap { net.minecraft.command.arguments.EntityArgument }) {
            val entityArgumentClass = classLoader.loadClass(this())
            entityArgumentClass.getDeclaredMethod(entities)
        }
    }

    fun entity(): ArgumentType<Any> = entityMethod.invoke(null) as ArgumentType<Any>

    fun entities(): ArgumentType<Any> = entitiesMethod.invoke(null) as ArgumentType<Any>
}
