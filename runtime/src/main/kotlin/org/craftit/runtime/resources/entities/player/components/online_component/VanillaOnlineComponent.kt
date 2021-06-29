package org.craftit.runtime.resources.entities.player.components.online_component

import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import org.craftit.api.Server
import org.craftit.api.Text
import org.craftit.api.chat.ChatParticipant
import org.craftit.api.chat.Message
import org.craftit.api.resources.entities.player.NativePlayer
import org.craftit.api.resources.entities.player.components.OnlineComponent
import org.craftit.api.resources.entities.player.connector.Connector
import org.craftit.api.resources.entities.player.input_resolver.InputResolver
import org.craftit.api.resources.entities.player.presenter.Presenter

class VanillaOnlineComponent @AssistedInject constructor(
    val inputResolver: InputResolver,
    val presenter: Presenter,
    private val connector: Connector,
    @Assisted val nativePlayer: NativePlayer
) : OnlineComponent {

    @AssistedFactory
    interface Factory {
        fun create(nativePlayer: NativePlayer): VanillaOnlineComponent
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
