package org.craftit.api.resources.entities.player.components

import org.craftit.api.Text
import org.craftit.api.chat.ChatParticipant
import org.craftit.api.chat.Message
import org.craftit.api.resources.entities.player.NativePlayer
import org.craftit.api.resources.entities.player.Player
import org.craftit.api.resources.entities.player.connector.Connector
import org.craftit.api.resources.entities.player.input_resolver.InputResolver
import org.craftit.api.resources.entities.player.presenter.Presenter

class OnlineComponent(
    val inputResolver: InputResolver,
    val presenter: Presenter,
    private val connector: Connector,
    val nativePlayer: NativePlayer
) : PlayerComponent {

    class Factory(
        private val inputResolverFactory: InputResolver.Factory,
        private val presenterFactory: Presenter.Factory,
        private val connectorFactory: Connector.Factory,
    ) {
        fun create(player: Player, nativePlayer: NativePlayer) = OnlineComponent(
            inputResolverFactory.create(player),
            presenterFactory.create(player),
            connectorFactory.create(player, nativePlayer.connector),
            nativePlayer
        )
    }

    val messageQueue = mutableListOf<Message>()

    override fun onAdded() {
        connector.start()
    }

    fun sendMessage(content: Text) {
        messageQueue.add(Message(content))
        connector.sendUpdates()
    }

    fun sendMessage(content: Text, sender: ChatParticipant) {
        messageQueue.add(Message(content, sender))
        connector.sendUpdates()
    }
}
