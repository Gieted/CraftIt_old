package org.craftit.api.chat

import org.craftit.api.text.Text

data class Message(
    val content: Text,
    val sender: ChatParticipant?
)
