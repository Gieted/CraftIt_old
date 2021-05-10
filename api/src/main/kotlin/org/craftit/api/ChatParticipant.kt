package org.craftit.api

import org.craftit.api.text_utils.toText

interface ChatParticipant {
    fun sendMessage(content: String) = sendMessage(content.toText())

    fun sendMessage(content: Text)

    fun sendMessage(content: String, sender: ChatParticipant) = sendMessage(content.toText(), sender)

    fun sendMessage(content: Text, sender: ChatParticipant)
}
