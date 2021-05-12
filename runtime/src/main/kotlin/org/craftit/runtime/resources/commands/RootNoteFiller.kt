package org.craftit.runtime.resources.commands

import com.mojang.brigadier.arguments.IntegerArgumentType
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.builder.RequiredArgumentBuilder
import com.mojang.brigadier.tree.CommandNode
import org.craftit.api.resources.commands.parameters.EntityParameter
import org.craftit.api.resources.commands.parameters.NumericParameter
import org.craftit.api.resources.commands.parameters.OptionParameter
import org.craftit.api.resources.entities.player.Player
import javax.inject.Inject

class RootNoteFiller @Inject constructor(private val entityArgumentWrapper: EntityArgumentWrapper) {
    fun fillRootNote(rootNode: CommandNode<Any>, player: Player) {
        player.server.commands.root.getDefinition(player).variants.forEach { variation ->
            var currentNode = rootNode
            variation.parameters.map {
                when {
                    it is NumericParameter<*> && it.type == Int::class ->
                        RequiredArgumentBuilder.argument<Any, Int>(
                            it.name, IntegerArgumentType.integer(it.min as Int, it.max as Int)
                        ).build()
                    it is EntityParameter -> RequiredArgumentBuilder.argument<Any, Any>(
                        it.name,
                        if (it.multiple) entityArgumentWrapper.entities() else entityArgumentWrapper.entity()
                    ).build()

                    it is OptionParameter -> currentNode.getChild(it.name)
                        ?: LiteralArgumentBuilder.literal<Any>(it.name).build()
                    else -> throw AssertionError()
                }
            }.forEach {
                currentNode.addChild(it)
                currentNode = it
            }
        }
    }
}
