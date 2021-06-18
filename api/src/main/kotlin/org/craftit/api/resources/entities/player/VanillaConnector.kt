package org.craftit.api.resources.entities.player

import org.craftit.api.resources.packets.DisplayMessagePacket
import org.craftit.api.resources.packets.SendChatMessagePacket
import java.util.*

class VanillaConnector(
    packetHandler: PacketHandler,
    private val nativeConnector: NativeConnector,
    private val player: Player
) : Connector {

    init {
        nativeConnector.onPacket {
            when (it) {
                is SendChatMessagePacket -> packetHandler.onChatMessage(it)
            }
        }
    }

    override fun update() {
        val model = player.presenter.present()
        model.messages.forEach {
            val type = when (it.type) {
                PlayerPresenter.Model.ChatMessage.Type.Chat -> DisplayMessagePacket.MessageType.Chat
                else -> DisplayMessagePacket.MessageType.System
            }
            nativeConnector.sendPacket(
                DisplayMessagePacket(
                    "minecraft:display_message",
                    it.content,
                    type,
                    it.sender ?: UUID(0, 0)
                )
            )
        }
    }
}
