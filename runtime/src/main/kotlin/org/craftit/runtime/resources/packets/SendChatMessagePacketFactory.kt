package org.craftit.runtime.resources.packets

import org.craftit.api.resources.packets.SendChatMessagePacket
import javax.inject.Inject

class SendChatMessagePacketFactory @Inject constructor() {
    fun create(message: String) = SendChatMessagePacket("minecraft:send_chat_message", message)
}
