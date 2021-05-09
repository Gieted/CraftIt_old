package org.craftit.api.resources.entities.player

import org.craftit.api.ChatParticipant

interface NativePlayer {
    var health: Int

    fun sendMessage(content: String)
    fun sendMessage(content: String, sender: ChatParticipant)
}
