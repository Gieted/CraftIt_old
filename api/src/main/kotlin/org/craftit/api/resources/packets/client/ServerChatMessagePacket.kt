package org.craftit.api.resources.packets.client

import org.craftit.api.resources.packets.Packet
import org.craftit.api.text.Text
import java.util.*

data class ServerChatMessagePacket(
    override val id: String,
    val message: Text,
    val type: MessageType,
    val sender: UUID
) : Packet {

    enum class MessageType {
        Chat, System, GameInfo
    }
}
