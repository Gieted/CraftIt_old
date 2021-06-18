package org.craftit.api.resources.entities.player

import org.craftit.api.*
import java.util.*

class VanillaPlayer(
    override val nativePlayer: NativePlayer,
    inputResolverFactory: VanillaInputResolver.Factory,
    controllerFactory: VanillaPlayerController.Factory,
    packetHandlerFactory: VanillaPacketHandler.Factory,
    override val server: Server,
    override val presenter: PlayerPresenter
) : Player {

    class Factory(
        private val inputResolverFactory: VanillaInputResolver.Factory,
        private val controllerFactory: VanillaPlayerController.Factory,
        private val packetHandlerFactory: VanillaPacketHandler.Factory,
        private val server: Server,
        private val presenter: PlayerPresenter
    ) {
        fun create(nativePlayer: NativePlayer) =
            VanillaPlayer(
                nativePlayer,
                inputResolverFactory,
                controllerFactory,
                packetHandlerFactory,
                server,
                presenter
            )
    }

    override val id: String
        get() = TODO("Not yet implemented")

    override val inputResolver: VanillaInputResolver = inputResolverFactory.create(this)
    override val controller: VanillaPlayerController = controllerFactory.create(this)
    override val connector: VanillaConnector =
        VanillaConnector(packetHandlerFactory.create(this), nativePlayer.connector, this)

    override fun sendSystemMessage(content: Text) {
        presenter.addSystemMessage(content)
        connector.update()
    }

    override fun sendChatMessage(content: Text, sender: ChatParticipant) {
        presenter.addChatMessage(content, UUID(0, 0))
        connector.update()
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
