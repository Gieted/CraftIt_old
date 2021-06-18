package org.craftit.api.resources.entities.player

import org.craftit.api.resources.packets.Packet

interface NativeConnector {

    fun onPacket(listener: (Packet) -> Unit)
    
    fun sendPacket(packet: Packet)
}
