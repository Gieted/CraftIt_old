package org.craftit.api.resources.entities

import org.craftit.api.resources.entities.player.components.PlayerComponent

data class PlayerPrototype(var components: MutableList<PlayerComponent> = mutableListOf())
