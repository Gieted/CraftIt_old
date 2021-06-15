package org.craftit.api.resources.entities.player

import org.craftit.api.resources.packets.SendChatMessagePacket

class VanillaPacketResolver private constructor(private val player: Player): PacketResolver {
    class Factory {
        fun create(player: Player) = VanillaPacketResolver(player)
    }
    
    override fun onChatMessage(packet: SendChatMessagePacket) {
        player.inputResolver.onChat(packet.message)
    }
}
