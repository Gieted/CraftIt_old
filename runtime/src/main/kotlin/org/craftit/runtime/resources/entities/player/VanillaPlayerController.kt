package org.craftit.runtime.resources.entities.player

import org.craftit.api.resources.entities.player.Player
import org.craftit.api.resources.entities.player.controller.PlayerController
import javax.inject.Inject

class VanillaPlayerController @Inject constructor(private val player: Player) : PlayerController {
    
    override fun executeCommand(command: String) {
        player.server.commands.root.execute(player, command)
    }
}
