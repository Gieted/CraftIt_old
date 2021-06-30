package org.craftit.runtime.resources.entities.player

import org.craftit.api.resources.entities.player.Player
import org.craftit.runtime.server.ServerScope
import java.util.*
import javax.inject.Inject

@ServerScope
class PlayerFactory @Inject constructor(
    private val vanillaPlayerFactory: VanillaPlayer.Factory
) {
    
    fun create(uuid: UUID): Player {
        return vanillaPlayerFactory.create(uuid)
    }
}
