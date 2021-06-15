package org.craftit.api.resources.entities.player

import org.craftit.api.resources.packets.SendChatMessagePacket

interface PacketResolver {
    
    fun onChatMessage(packet: SendChatMessagePacket)
}
