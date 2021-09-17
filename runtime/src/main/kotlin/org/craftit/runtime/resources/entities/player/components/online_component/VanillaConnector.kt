package org.craftit.runtime.resources.entities.player.components.online_component

import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import org.craftit.api.resources.entities.player.*
import org.craftit.api.resources.entities.player.connector.Connector
import org.craftit.api.resources.entities.player.connector.NativeConnector
import org.craftit.api.resources.entities.player.connector.packet_handler.PacketHandler
import org.craftit.api.resources.packets.client.ServerChatMessagePacket
import org.craftit.api.resources.packets.server.ClientChatMessagePacket
import java.util.*

class VanillaConnector @AssistedInject constructor(
    private val packetHandler: PacketHandler,
    @Assisted private val nativeConnector: NativeConnector,
    private val player: Player
) : Connector {

    @AssistedFactory
    interface Factory: Connector.Factory {
        override fun create(nativeConnector: NativeConnector): VanillaConnector
    }

    override fun connect() {
        nativeConnector.onPacket {
            when (it) {
                is ClientChatMessagePacket -> packetHandler.onChatMessage(it)
            }
        }
    }

    override fun sendUpdates() {
    }
}
