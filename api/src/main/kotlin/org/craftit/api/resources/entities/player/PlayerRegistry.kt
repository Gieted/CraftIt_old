package org.craftit.api.resources.entities.player

import org.craftit.api.resources.entities.PlayerPrototype
import org.craftit.api.resources.entities.player.components.PlayerComponentsRegistry

interface PlayerRegistry {
    var prototype: PlayerPrototype
    
    operator fun get(id: String): Player?
    
    fun create(id: String): Player

    fun getOrCreate(id: String) = get(id) ?: create(id)
    
    val components: PlayerComponentsRegistry
    
    val online: OnlinePlayerRegistry
    
    var factory: Player.Factory
}
