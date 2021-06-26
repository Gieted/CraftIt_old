package org.craftit.api.resources.entities.player

import org.craftit.api.resources.entities.player.components.PlayerComponentRegistry
import java.util.*

interface PlayerRegistry {
    operator fun get(id: String): Player?
    
    fun getByUUID(uuid: UUID): Player?
    
    fun create(uuid: UUID): Player
    
    val components: PlayerComponentRegistry
}
