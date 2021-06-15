package org.craftit.api.resources.entities.player

import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import org.craftit.api.resources.commands.Command

class VanillaPlayerController @AssistedInject constructor(@Assisted private val player: Player) : PlayerController {
    @AssistedFactory
    interface Factory {
        fun create(player: Player): VanillaPlayerController
    }

    override fun executeCommand(command: String) {
        player.server.commands.root.execute(player, command)
    }
}
