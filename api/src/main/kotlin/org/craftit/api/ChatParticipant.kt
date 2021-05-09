package org.craftit.api

interface ChatParticipant {
    fun sendMessage(content: String)

    fun sendMessage(content: String, sender: ChatParticipant)
}
