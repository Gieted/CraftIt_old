package org.craftit.runtime.resources.entities.player

import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import org.craftit.api.resources.entities.player.connector.NativeConnector
import org.craftit.api.resources.packets.DisplayMessagePacket
import org.craftit.api.resources.packets.Packet
import org.craftit.runtime.resources.packets.converters.PacketConverter
import org.craftit.runtime.source_maps.SourceMap
import javax.inject.Named

@Suppress("LocalVariableName")
class NativeConnectorImpl @AssistedInject constructor(
    @Assisted private val playNetHandler: Any,
    private val sourceMap: SourceMap,
    private val packetConverter: PacketConverter,
    @Named("server") private val classLoader: ClassLoader
) : NativeConnector {

    @AssistedFactory
    interface Factory {
        fun create(playNetHandler: Any): NativeConnectorImpl
    }
    
    private val listeners = mutableListOf<(Packet) -> Unit>()

    private val sendMethod = run {
        with(sourceMap { net.minecraft.network.play.ServerPlayNetHandler }) {
            val playNetHandler = classLoader.loadClass(this())

            val IPacket = classLoader.loadClass(sourceMap { net.minecraft.network.IPacket }())

            playNetHandler.getDeclaredMethod(send, IPacket)
        }
    }

    override fun onPacket(listener: (Packet) -> Unit) {
        listeners.add(listener)
    }

    override fun sendPacket(packet: DisplayMessagePacket) {
        sendMethod.invoke(playNetHandler, packetConverter.convert(packet))
    }

    fun notify(packet: Packet) {
        listeners.forEach {
            it(packet)
        }
    }
}
