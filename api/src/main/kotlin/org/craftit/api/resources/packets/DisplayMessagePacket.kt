package org.craftit.api.resources.packets

import org.craftit.api.Text
import java.util.*

data class DisplayMessagePacket(override val id: String, val message: Text, val type: MessageType, val sender: UUID) : Packet {
    enum class MessageType {
        Chat, System, GameInfo
    }
}
