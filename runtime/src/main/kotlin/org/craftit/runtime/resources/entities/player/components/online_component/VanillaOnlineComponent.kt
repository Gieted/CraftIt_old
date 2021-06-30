package org.craftit.runtime.resources.entities.player.components.online_component

import org.craftit.api.Text
import org.craftit.api.chat.ChatParticipant
import org.craftit.api.chat.Message
import org.craftit.api.resources.entities.player.NativePlayer
import org.craftit.api.resources.entities.player.components.OnlineComponent
import org.craftit.api.resources.entities.player.connector.Connector
import org.craftit.api.resources.entities.player.input_resolver.InputResolver
import org.craftit.api.resources.entities.player.presenter.Presenter
import javax.inject.Inject

class VanillaOnlineComponent @Inject constructor(
    val inputResolver: InputResolver,
    val presenter: Presenter,
    private val connector: Connector,
    val nativePlayer: NativePlayer
) : OnlineComponent {

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
