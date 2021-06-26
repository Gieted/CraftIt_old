package org.craftit.api.resources.entities.player.input_resolver

import org.craftit.api.resources.entities.player.Player

interface InputResolver {

    fun interface Factory {
        fun create(player: Player): InputResolver
    }
    
    fun onChat(message: String)
}
