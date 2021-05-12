package org.craftit.api

import org.craftit.api.text_utils.invoke
import org.craftit.api.text_utils.toText

interface ChatParticipant {
    fun sendSystemMessage(content: String) = sendSystemMessage(content.toText())

    fun sendSystemMessage(content: Text)
    
    fun sendErrorMessage(content: String) = sendSystemMessage(content(Color.red))

    fun sendChatMessage(content: String, sender: ChatParticipant) = sendChatMessage(content.toText(), sender)

    fun sendChatMessage(content: Text, sender: ChatParticipant)
}
