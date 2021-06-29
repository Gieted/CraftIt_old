package org.craftit.api.resources.entities.player.components

import org.craftit.api.Text
import org.craftit.api.chat.ChatParticipant

interface OnlineComponent: PlayerComponent {
    fun sendMessage(content: Text)

    fun sendMessage(content: Text, sender: ChatParticipant)
}
