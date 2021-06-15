package org.craftit.api.resources.entities.player

import org.craftit.api.*

class VanillaPlayer(
    override val nativePlayer: NativePlayer,
    inputResolverFactory: VanillaInputResolver.Factory,
    controllerFactory: VanillaPlayerController.Factory,
    packetResolverFactory: VanillaPacketResolver.Factory,
    override val server: Server
) : Player {

    class Factory(
        private val inputResolverFactory: VanillaInputResolver.Factory,
        private val controllerFactory: VanillaPlayerController.Factory,
        private val packetResolverFactory: VanillaPacketResolver.Factory,
        private val server: Server
    ) {
        fun create(nativePlayer: NativePlayer) =
            VanillaPlayer(nativePlayer, inputResolverFactory, controllerFactory, packetResolverFactory, server)
    }

    override val id: String
        get() = TODO("Not yet implemented")

    override val inputResolver: VanillaInputResolver = inputResolverFactory.create(this)
    override val controller: VanillaPlayerController = controllerFactory.create(this)
    override val packetResolver: VanillaPacketResolver = packetResolverFactory.create(this)


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
