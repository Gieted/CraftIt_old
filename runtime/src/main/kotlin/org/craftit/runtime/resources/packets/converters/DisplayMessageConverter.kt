package org.craftit.runtime.resources.packets.converters

import org.craftit.api.resources.packets.DisplayMessagePacket
import org.craftit.runtime.resources.entities.player.ChatType
import org.craftit.runtime.source_maps.SourceMap
import org.craftit.runtime.text.StringTextComponentFactory
import javax.inject.Inject

class DisplayMessageConverter @Inject constructor(
    private val sourceMap: SourceMap,
    private val classLoader: ClassLoader,
    private val stringTextComponentFactory: StringTextComponentFactory,
    private val chatType: ChatType
) {
    val constructor = run {
        with(sourceMap { net.minecraft.network.play.server.SChatPacket }) {
            val sChatPacket = classLoader.loadClass(this())
            
            sChatPacket.constructors.find { it.parameterCount == 3 }!!
        }
    }

    fun convert(packet: DisplayMessagePacket): Any {
        val chatType = when (packet.type) {
            DisplayMessagePacket.MessageType.Chat -> chatType.CHAT
            DisplayMessagePacket.MessageType.System -> chatType.SYSTEM
            else -> chatType.GAME_INFO
        }
        
        return constructor.newInstance(stringTextComponentFactory.create(packet.message), chatType, packet.sender)
    }
}
