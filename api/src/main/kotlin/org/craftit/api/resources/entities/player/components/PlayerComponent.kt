package org.craftit.api.resources.entities.player.components

import org.craftit.api.resources.components.Component
import org.craftit.api.resources.entities.player.Player

interface PlayerComponent : Component {
    interface Factory {
        fun create(player: Player)
    }
}
