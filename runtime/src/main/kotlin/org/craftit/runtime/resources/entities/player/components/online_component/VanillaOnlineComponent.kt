package org.craftit.runtime.resources.entities.player.components.online_component

import dagger.assisted.AssistedInject
import org.craftit.api.Text
import org.craftit.api.chat.ChatParticipant
import org.craftit.api.chat.Message
import org.craftit.api.resources.entities.player.NativePlayer
import org.craftit.api.resources.entities.player.Player
import org.craftit.api.resources.entities.player.components.online.OnlineComponent
import org.craftit.api.resources.entities.player.connector.Connector
import org.craftit.api.resources.entities.player.input_resolver.InputResolver
import org.craftit.api.resources.entities.player.presenter.Presenter
import org.craftit.runtime.server.ServerScope
import javax.inject.Inject

class VanillaOnlineComponent @AssistedInject constructor(
    val inputResolver: InputResolver,
    val presenter: Presenter,
    private val connector: Connector,
    val nativePlayer: NativePlayer,
) : OnlineComponent {

    @ServerScope
    class Factory @Inject constructor(
        private val onlineComponentComponentFactory: OnlineComponentComponent.Factory
    ) : OnlineComponent.Factory {
        override fun create(player: Player, nativePlayer: NativePlayer): VanillaOnlineComponent {
            val component = onlineComponentComponentFactory.create(player)

            return VanillaOnlineComponent(
                component.inputResolver(),
                component.presenter(),
                component.connectorFactory().create(nativePlayer.connector),
                nativePlayer
            )
        }
    }

    val messageQueue = mutableListOf<Message>()

    override fun onAdded() {
        connector.start()
    }

    override fun sendMessage(content: Text) {
        messageQueue.add(Message(content))
        connector.sendUpdates()
    }

    override fun sendMessage(content: Text, sender: ChatParticipant) {
        messageQueue.add(Message(content, sender))
        connector.sendUpdates()
    }
}
