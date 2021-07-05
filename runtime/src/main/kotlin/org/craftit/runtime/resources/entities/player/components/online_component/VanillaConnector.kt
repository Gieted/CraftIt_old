package org.craftit.runtime.resources.entities.player.components.online_component

import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import org.craftit.api.resources.entities.player.*
import org.craftit.api.resources.entities.player.connector.Connector
import org.craftit.api.resources.entities.player.connector.NativeConnector
import org.craftit.api.resources.entities.player.connector.packet_handler.PacketHandler
import org.craftit.api.resources.packets.DisplayMessagePacket
import org.craftit.api.resources.packets.SendChatMessagePacket
import org.craftit.runtime.resources.entities.player.PlayerScope
import java.util.*
import javax.inject.Inject

class VanillaConnector @AssistedInject constructor(
    private val packetHandler: PacketHandler,
    @Assisted private val nativeConnector: NativeConnector,
    private val player: Player
) : Connector {

    @AssistedFactory
    interface Factory: Connector.Factory {
        override fun create(nativeConnector: NativeConnector): VanillaConnector
    }

    override fun start() {
        nativeConnector.onPacket {
            when (it) {
                is SendChatMessagePacket -> packetHandler.onChatMessage(it)
            }
        }
    }

    override fun sendUpdates() {
        val model = player.components[VanillaOnlineComponent::class]!!.presenter.present()
        model.messages.forEach {
            nativeConnector.sendPacket(
                DisplayMessagePacket(
                    "minecraft:display_message",
                    it.content,
                    if (it.sender == null) DisplayMessagePacket.MessageType.System else DisplayMessagePacket.MessageType.Chat,
                    UUID(0, 0)
                )
            )
        }
    }
}
