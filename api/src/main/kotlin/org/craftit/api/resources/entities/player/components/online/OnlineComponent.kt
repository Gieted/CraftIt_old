package org.craftit.api.resources.entities.player.components.online

import org.craftit.api.text.Text
import org.craftit.api.chat.ChatParticipant
import org.craftit.api.resources.entities.player.NativePlayer
import org.craftit.api.resources.entities.player.Player
import org.craftit.api.resources.entities.player.components.PlayerComponent

interface OnlineComponent: PlayerComponent {

    interface Factory {
        fun create(player: Player, nativePlayer: NativePlayer): OnlineComponent
    }

    fun sendMessage(content: Text, sender: ChatParticipant?)
}
