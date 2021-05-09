package org.craftit.runtime.resources.commands

import com.mojang.brigadier.arguments.ArgumentType
import com.mojang.brigadier.arguments.IntegerArgumentType
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.builder.RequiredArgumentBuilder
import com.mojang.brigadier.tree.CommandNode
import org.craftit.api.resources.commands.parameters.EntityParameter
import org.craftit.api.resources.commands.parameters.NumericParameter
import org.craftit.api.resources.entities.player.Player
import javax.inject.Inject

class RootNoteFiller @Inject constructor(private val entityArgumentWrapper: EntityArgumentWrapper) {
    fun fillRootNote(rootNode: CommandNode<Any>, player: Player) {
        player.server.commands.map { command ->
            val commandNode = LiteralArgumentBuilder.literal<Any>(command.id).build()
            command.getDefinition(player).variations.forEach { variation ->
                var currentNode: CommandNode<Any> = commandNode
                variation.parameters.map {
                    when {
                        it is NumericParameter<*> && it.type == Int::class ->
                            RequiredArgumentBuilder.argument(
                                it.name,
                                IntegerArgumentType.integer(it.min as Int, it.max as Int)
                            )
                        it is EntityParameter -> RequiredArgumentBuilder.argument<Any, Any>(
                            it.name,
                            if (it.multiple) entityArgumentWrapper.entities() else entityArgumentWrapper.entity()
                        )
                        else -> throw AssertionError()
                    }.build()
                }.forEach {
                    currentNode.addChild(it)
                    currentNode = it
                }
            }
            commandNode
        }.forEach { node -> rootNode.addChild(node) }
    }
}
