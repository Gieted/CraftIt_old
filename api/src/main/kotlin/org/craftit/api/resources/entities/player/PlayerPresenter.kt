package org.craftit.api.resources.entities.player

import org.craftit.api.Text
import java.util.*

interface PlayerPresenter {

    fun present(): Model
    
    data class Model(
        val messages: List<ChatMessage>
    ) {
        data class ChatMessage(val content: Text, val type: Type, val sender: UUID?) {
            enum class Type {
                Chat, System
            }
        }
    }
    
    fun addChatMessage(content: Text, sender: UUID)
    
    fun addSystemMessage(content: Text)
}
