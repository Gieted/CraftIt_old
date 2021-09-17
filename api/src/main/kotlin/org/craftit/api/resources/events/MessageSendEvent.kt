package org.craftit.api.resources.events

import org.craftit.api.chat.Message

data class MessageSendEvent(override val id: String, val message: Message) : Event
