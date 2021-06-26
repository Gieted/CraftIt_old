package org.craftit.api.resources.entities

import org.craftit.api.resources.entities.player.PlayerRegistry

interface EntityRegistry {
    val players: PlayerRegistry
    
    operator fun get(id: String): Entity?
}
