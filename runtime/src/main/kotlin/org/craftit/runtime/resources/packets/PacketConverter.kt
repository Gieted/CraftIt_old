package org.craftit.runtime.resources.packets

import javax.inject.Inject

class PacketConverter @Inject constructor(
    private val cChatMessageConverter: CChatMessageConverter
) {

    fun convertCChatMessage(nativePacket: Any) = cChatMessageConverter.convert(nativePacket)
}
