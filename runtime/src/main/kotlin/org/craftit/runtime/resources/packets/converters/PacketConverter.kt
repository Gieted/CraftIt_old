package org.craftit.runtime.resources.packets.converters

import org.craftit.api.resources.packets.DisplayMessagePacket
import org.craftit.api.resources.packets.Packet
import javax.inject.Inject

class PacketConverter @Inject constructor(
    private val sendChatMessageConverter: SendChatMessageConverter,
    private val displayMessageConverter: DisplayMessageConverter
) {

    fun convertCChatMessage(nativePacket: Any) = sendChatMessageConverter.convert(nativePacket)

    fun convert(packet: Packet): Any =
        when (packet) {
            is DisplayMessagePacket -> displayMessageConverter.convert(packet)
            else -> AssertionError()
        }
}
