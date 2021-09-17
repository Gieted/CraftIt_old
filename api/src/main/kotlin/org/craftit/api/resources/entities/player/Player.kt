package org.craftit.api.resources.entities.player

import org.craftit.api.Server
import org.craftit.api.resources.components.ComponentStore
import org.craftit.api.resources.entities.Entity
import org.craftit.api.resources.entities.HealthHolder
import org.craftit.api.resources.entities.player.components.PlayerComponent
import org.craftit.api.resources.entities.player.controller.PlayerController

interface Player : Entity, HealthHolder {
    
    interface Factory {
        fun create(id: String): Player
    }
    
    val controller: PlayerController
    val server: Server

    val components: ComponentStore<PlayerComponent>
}
