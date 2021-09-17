package org.craftit.api.resources.entities.player.connector

import org.craftit.api.resources.packets.client.ServerChatMessagePacket
import org.craftit.api.resources.packets.Packet

interface NativeConnector {

    fun onPacket(listener: (Packet) -> Unit)
    
    fun sendPacket(packet: ServerChatMessagePacket)
}
