package org.craftit.runtime.resources.commands

import com.mojang.brigadier.arguments.ArgumentType
import org.craftit.runtime.server.ServerScope
import org.craftit.runtime.source_maps.SourceMap
import java.lang.reflect.Method
import javax.inject.Inject
import javax.inject.Named

@Suppress("UNCHECKED_CAST")
@ServerScope
class EntityArgumentWrapper @Inject constructor(
    sourceMap: SourceMap,
    classLoader: ClassLoader
) {
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

    fun <T> entity(): ArgumentType<T> = entityMethod.invoke(null) as ArgumentType<T>

    fun <T> entities(): ArgumentType<T> = entitiesMethod.invoke(null) as ArgumentType<T>
}
