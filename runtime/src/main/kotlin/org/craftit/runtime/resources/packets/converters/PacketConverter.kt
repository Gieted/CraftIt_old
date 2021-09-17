package org.craftit.runtime.resources.packets.converters

import org.craftit.api.resources.packets.client.ServerChatMessagePacket
import org.craftit.runtime.server.ServerScope
import javax.inject.Inject

@ServerScope
class PacketConverter @Inject constructor(
    private val sendChatMessageConverter: SendChatMessageConverter,
    private val displayMessageConverter: DisplayMessageConverter
) {
    fun convertCChatMessage(nativePacket: Any) = sendChatMessageConverter.convert(nativePacket)

    fun convert(packet: ServerChatMessagePacket): Any = displayMessageConverter.convert(packet)
}
