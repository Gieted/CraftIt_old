package org.craftit.api.resources.packets

data class SendChatMessagePacket(override val id: String, val message: String): Packet
