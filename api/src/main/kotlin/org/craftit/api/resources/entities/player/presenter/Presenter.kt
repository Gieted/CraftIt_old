package org.craftit.api.resources.entities.player.presenter

import org.craftit.api.resources.entities.player.Player
import org.craftit.api.resources.events.Event

interface Presenter {

    fun interface Factory {
        fun create(player: Player): Presenter
    }

    fun present(): Model
    
    data class Model(
        val events: List<Event>
    )
}
