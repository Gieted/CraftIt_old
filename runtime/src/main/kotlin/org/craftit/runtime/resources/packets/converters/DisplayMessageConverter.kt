package org.craftit.runtime.resources.packets.converters

import org.craftit.api.resources.packets.client.ServerChatMessagePacket
import org.craftit.runtime.resources.entities.player.ChatType
import org.craftit.runtime.server.ServerScope
import org.craftit.runtime.source_maps.SourceMap
import org.craftit.runtime.text.NativeStringTextComponentConverter
import javax.inject.Inject

@ServerScope
class DisplayMessageConverter @Inject constructor(
    private val sourceMap: SourceMap,
    private val classLoader: ClassLoader,
    private val stringTextComponentFactory: NativeStringTextComponentConverter,
    private val chatType: ChatType
) {
    val constructor = run {
        with(sourceMap { net.minecraft.network.play.server.SChatPacket }) {
            val sChatPacket = classLoader.loadClass(this())
            
            sChatPacket.constructors.find { it.parameterCount == 3 }!!
        }
    }

    fun convert(packet: ServerChatMessagePacket): Any {
        val chatType = when (packet.type) {
            ServerChatMessagePacket.MessageType.Chat -> chatType.CHAT
            ServerChatMessagePacket.MessageType.System -> chatType.SYSTEM
            else -> chatType.GAME_INFO
        }
        
        return constructor.newInstance(stringTextComponentFactory.convert(packet.message), chatType, packet.sender)
    }
}
