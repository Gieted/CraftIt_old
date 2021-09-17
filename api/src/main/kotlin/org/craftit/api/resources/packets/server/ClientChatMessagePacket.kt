package org.craftit.api.resources.packets.server

import org.craftit.api.resources.packets.Packet

data class ClientChatMessagePacket(override val id: String, val message: String): Packet
