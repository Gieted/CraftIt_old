package org.craftit.runtime.resources.commands

import com.mojang.brigadier.tree.CommandNode
import org.craftit.api.resources.entities.player.Player
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
class RootNoteFiller @Inject constructor(private val parameterConverter: ParameterConverter) {
    fun fillRootNote(rootNode: CommandNode<Any>, player: Player) {
        player.server.commands.root.getDefinition(player).rootParameters
            .map {
                with(parameterConverter) {
                    it.toBrigadierCommandNode<Any>()
                }
            }.forEach { rootNode.addChild(it) }
    }
}
