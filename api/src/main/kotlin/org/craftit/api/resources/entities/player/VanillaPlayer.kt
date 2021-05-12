package org.craftit.api.resources.entities.player

import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import org.craftit.api.*

class VanillaPlayer @AssistedInject constructor(
    @Assisted override val nativePlayer: NativePlayer,
    inputResolverFactory: VanillaInputResolver.Factory,
    controllerFactory: VanillaPlayerController.Factory,
    override val server: Server
) : Player {
    @AssistedFactory
    interface Factory {
        fun create(nativePlayer: NativePlayer): VanillaPlayer
    }

    override val id: String
        get() = TODO("Not yet implemented")

    override val inputResolver: InputResolver = inputResolverFactory.create(this)
    override val controller: VanillaPlayerController = controllerFactory.create(this)


    override fun sendSystemMessage(content: Text) {
        nativePlayer.sendMessage(content)
    }

    override fun sendChatMessage(content: Text, sender: ChatParticipant) {
        nativePlayer.sendMessage(content, sender)
    }

    override var health: Int
        get() = nativePlayer.health
        set(value) {
            nativePlayer.health = value
        }

    override fun heal(amount: Int) {
        if (amount > 0) {
            health += amount
        }
    }
}
