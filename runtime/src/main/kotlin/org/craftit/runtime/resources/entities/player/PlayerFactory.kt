package org.craftit.runtime.resources.entities.player

import org.craftit.api.resources.entities.player.Player
import org.craftit.api.resources.entities.player.VanillaPlayer
import javax.inject.Inject

class PlayerFactory @Inject constructor(
    private val nativePlayerFactory: ServerPlayerEntityWrapper.Factory,
    private val vanillaPlayerFactory: VanillaPlayer.Factory
) {
    fun create(serverPlayerEntity: Any): Player {
        return vanillaPlayerFactory.create(nativePlayerFactory.create(serverPlayerEntity))
    }
}
