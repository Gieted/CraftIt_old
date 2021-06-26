package org.craftit.api.chat

import org.craftit.api.Color
import org.craftit.api.Text
import org.craftit.api.text_utils.invoke
import org.craftit.api.text_utils.toText

interface ChatParticipant {
    fun sendMessage(content: String) = sendMessage(content.toText())

    fun sendMessage(content: Text)
    
    fun sendErrorMessage(content: String) = sendMessage(content(Color.red))

    fun sendMessage(content: String, sender: ChatParticipant) = sendMessage(content.toText(), sender)

    fun sendMessage(content: Text, sender: ChatParticipant)
}
