package org.craftit.api.resources.entities.player

import org.craftit.api.ChatParticipant
import org.craftit.api.Text

interface NativePlayer {
    var health: Int

    fun sendMessage(content: Text, sender: ChatParticipant? = null)
}
