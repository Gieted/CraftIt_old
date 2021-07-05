package org.craftit.runtime.resources.entities.player

import org.craftit.api.resources.entities.player.Player
import org.craftit.api.resources.entities.player.input_resolver.InputResolver
import org.craftit.runtime.resources.entities.player.components.online_component.OnlineComponentScope
import javax.inject.Inject

@OnlineComponentScope
class VanillaInputResolver @Inject constructor(private val player: Player) : InputResolver {
    override fun onChat(message: String) {
        fun String.isCommand() = startsWith("/")

        if (message.isCommand()) {
            player.controller.executeCommand(message.drop(1))
        }
    }
}
