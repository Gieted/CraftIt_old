package org.craftit.runtime.resources.entities.player.components.online_component

import org.craftit.api.resources.entities.player.Player
import org.craftit.api.resources.entities.player.connector.packet_handler.PacketHandler
import org.craftit.api.resources.packets.SendChatMessagePacket
import org.craftit.runtime.resources.entities.player.PlayerScope
import javax.inject.Inject

@OnlineComponentScope
class VanillaPacketHandler @Inject constructor(private val player: Player) : PacketHandler {

    override fun onChatMessage(packet: SendChatMessagePacket) {
        player.components[VanillaOnlineComponent::class]!!.inputResolver.onChat(packet.message)
    }
}
