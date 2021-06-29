package org.craftit.runtime.resources.entities.player.components.online_component

import org.craftit.api.resources.entities.player.Player
import org.craftit.api.resources.entities.player.connector.packet_handler.PacketHandler
import org.craftit.api.resources.packets.SendChatMessagePacket

class VanillaPacketHandler constructor(private val player: Player): PacketHandler {
    
    override fun onChatMessage(packet: SendChatMessagePacket) {
        player.components[VanillaOnlineComponent::class]!!.inputResolver.onChat(packet.message)
    }
}
