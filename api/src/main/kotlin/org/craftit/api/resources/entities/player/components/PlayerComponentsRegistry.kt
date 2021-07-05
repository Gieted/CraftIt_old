package org.craftit.api.resources.entities.player.components

import org.craftit.api.resources.entities.player.components.online.OnlineComponentRegistry

interface PlayerComponentsRegistry {
    val onlineComponent: OnlineComponentRegistry
}
