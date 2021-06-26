package org.craftit.api.resources.entities.player.input_resolver

import org.craftit.api.resources.entities.player.Player

class VanillaInputResolver(private val player: Player) : InputResolver {
    override fun onChat(message: String) {
        fun String.isCommand() = startsWith("/")

        if (message.isCommand()) {
            player.controller.executeCommand(message.drop(1))
        }
    }
}
