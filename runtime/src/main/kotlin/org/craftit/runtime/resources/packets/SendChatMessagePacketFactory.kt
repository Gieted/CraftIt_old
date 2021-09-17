package org.craftit.runtime.resources.packets

import org.craftit.api.resources.packets.server.ClientChatMessagePacket
import javax.inject.Inject

class SendChatMessagePacketFactory @Inject constructor() {
    fun create(message: String) = ClientChatMessagePacket("minecraft:send_chat_message", message)
}
