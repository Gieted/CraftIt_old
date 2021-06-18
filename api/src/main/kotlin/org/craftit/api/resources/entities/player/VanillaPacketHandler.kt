package org.craftit.api.resources.entities.player

import org.craftit.api.resources.packets.SendChatMessagePacket

class VanillaPacketHandler private constructor(private val player: Player): PacketHandler {
    
    class Factory {
        fun create(player: Player) = VanillaPacketHandler(player)
    }
    
    override fun onChatMessage(packet: SendChatMessagePacket) {
        player.inputResolver.onChat(packet.message)
    }
}
