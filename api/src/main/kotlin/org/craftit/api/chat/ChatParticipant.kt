package org.craftit.api.chat

import org.craftit.api.Color
import org.craftit.api.text.Text
import org.craftit.api.text.invoke
import org.craftit.api.text.toText

interface ChatParticipant {
    fun sendMessage(content: String, sender: ChatParticipant? = null) = sendMessage(content.toText(), sender)

    fun sendMessage(content: Text, sender: ChatParticipant? = null)
    
    fun sendErrorMessage(content: String) = sendMessage(content(Color.red))
}
