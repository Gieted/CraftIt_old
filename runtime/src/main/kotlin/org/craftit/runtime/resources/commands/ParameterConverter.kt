package org.craftit.runtime.resources.commands

import com.mojang.brigadier.arguments.IntegerArgumentType
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.builder.RequiredArgumentBuilder
import com.mojang.brigadier.tree.ArgumentCommandNode
import com.mojang.brigadier.tree.CommandNode
import com.mojang.brigadier.tree.LiteralCommandNode
import org.craftit.api.resources.commands.parameters.EntityParameter
import org.craftit.api.resources.commands.parameters.IntParameter
import org.craftit.api.resources.commands.parameters.OptionParameter
import org.craftit.api.resources.commands.parameters.Parameter
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
class ParameterConverter @Inject constructor(private val entityArgumentWrapper: EntityArgumentWrapper) {
    
    fun <T> convertEntityParameter(parameter: EntityParameter): ArgumentCommandNode<T, Any> =
        RequiredArgumentBuilder.argument<T, Any>(
            parameter.name,
            if (parameter.multiple) entityArgumentWrapper.entities() else entityArgumentWrapper.entity()
        ).build().also { commandNode ->
            parameter.children.forEach { commandNode.addChild(it.toBrigadierCommandNode()) }
        }

    fun <T> convertIntParameter(parameter: IntParameter): ArgumentCommandNode<T, Int> =
        RequiredArgumentBuilder.argument<T, Int>(
            parameter.name, IntegerArgumentType.integer(parameter.min, parameter.max)
        ).build().also { commandNode ->
            parameter.children.forEach { commandNode.addChild(it.toBrigadierCommandNode()) }
        }

    fun <T> convertOptionParameter(parameter: OptionParameter): LiteralCommandNode<T> =
        LiteralArgumentBuilder.literal<T>(parameter.name).build().also { commandNode ->
            parameter.children.forEach { commandNode.addChild(it.toBrigadierCommandNode()) }
        }
}
