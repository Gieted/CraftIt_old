package org.craftit.api.resources.entities.player.connector.packet_handler

import org.craftit.api.resources.entities.player.components.OnlineComponent
import org.craftit.api.resources.entities.player.Player
import org.craftit.api.resources.packets.SendChatMessagePacket

class VanillaPacketHandler constructor(private val player: Player): PacketHandler {
    
    override fun onChatMessage(packet: SendChatMessagePacket) {
        player.components[OnlineComponent::class]!!.inputResolver.onChat(packet.message)
    }
}
