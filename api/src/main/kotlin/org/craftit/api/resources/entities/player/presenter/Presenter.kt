package org.craftit.api.resources.entities.player.presenter

import org.craftit.api.chat.Message
import org.craftit.api.resources.entities.player.Player

interface Presenter {

    fun interface Factory {
        fun create(player: Player): Presenter
    }

    fun present(): Model
    
    data class Model(
        val messages: List<Message>
    )
}
