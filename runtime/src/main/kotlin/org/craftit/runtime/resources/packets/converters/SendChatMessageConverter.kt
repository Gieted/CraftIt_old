package org.craftit.runtime.resources.packets.converters

import org.craftit.api.resources.packets.SendChatMessagePacket
import org.craftit.runtime.resources.packets.SendChatMessagePacketFactory
import org.craftit.runtime.source_maps.SourceMap
import java.lang.reflect.Method
import javax.inject.Inject
import javax.inject.Named

class SendChatMessageConverter @Inject constructor(
    private val sourceMap: SourceMap,
    private val sendChatMessagePacketFactory: SendChatMessagePacketFactory,
    @Named("server") classLoader: ClassLoader
) {
    private val getMessageMethod: Method = run {
        with(sourceMap { net.minecraft.network.play.client.CChatMessagePacket }) {
            val cChatMessage = classLoader.loadClass(this())
            cChatMessage.getDeclaredMethod(getMessage)
        }
    }

    fun convert(nativePacket: Any): SendChatMessagePacket =
        sendChatMessagePacketFactory.create(getMessageMethod.invoke(nativePacket) as String)
}
