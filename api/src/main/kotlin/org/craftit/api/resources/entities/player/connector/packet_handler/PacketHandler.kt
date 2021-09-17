package org.craftit.api.resources.entities.player.connector.packet_handler

import org.craftit.api.resources.entities.player.Player
import org.craftit.api.resources.packets.server.ClientChatMessagePacket


interface PacketHandler {

    fun interface Factory {
        fun create(player: Player): PacketHandler
    }
    
    fun onChatMessage(packet: ClientChatMessagePacket)
}
