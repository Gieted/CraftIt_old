package org.craftit.runtime.resources.entities.player

import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import org.craftit.api.Server
import org.craftit.api.resources.entities.player.Player
import org.craftit.api.resources.entities.player.controller.VanillaPlayerController
import java.util.*

class PlayerFactory @AssistedInject constructor(
    @Assisted private val server: Server,
    private val vanillaPlayerFactory: VanillaPlayer.Factory
) {
    @AssistedFactory
    interface Factory {
        fun create(server: Server): PlayerFactory
    }
    
    fun create(uuid: UUID): Player {
        return vanillaPlayerFactory.create(uuid, server)
    }
}
