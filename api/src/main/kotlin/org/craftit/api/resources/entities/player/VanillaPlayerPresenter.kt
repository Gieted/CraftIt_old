package org.craftit.api.resources.entities.player

import org.craftit.api.Text
import java.util.*

class VanillaPlayerPresenter: PlayerPresenter {
    private val messagesQueue = mutableListOf<PlayerPresenter.Model.ChatMessage>()
    
    override fun present(): PlayerPresenter.Model {
        val model = PlayerPresenter.Model(messagesQueue.toList())
        messagesQueue.clear()
        
        return model
    }

    override fun addChatMessage(content: Text, sender: UUID) {
        messagesQueue.add(PlayerPresenter.Model.ChatMessage(content, PlayerPresenter.Model.ChatMessage.Type.Chat, sender))
    }

    override fun addSystemMessage(content: Text) {
        messagesQueue.add(PlayerPresenter.Model.ChatMessage(content, PlayerPresenter.Model.ChatMessage.Type.Chat, null))
    }
}
