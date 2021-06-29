package org.craftit.runtime.resources.entities.player

import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import org.craftit.api.Server
import org.craftit.api.resources.entities.player.Player
import org.craftit.api.resources.entities.player.PlayerRegistry
import org.craftit.api.resources.entities.player.components.PlayerComponentRegistry
import org.craftit.runtime.resources.entities.player.components.online_component.VanillaOnlineComponent
import java.util.*

class RuntimePlayerRegistry @AssistedInject constructor(
    private val nativePlayerFactory: NativePlayerImpl.Factory,
    private val nativeConnectorFactory: NativeConnectorImpl.Factory,
    private val onlineComponentFactory: VanillaOnlineComponent.Factory,
    @Assisted server: Server,
    playerFactoryFactory: PlayerFactory.Factory
) : PlayerRegistry {

    @AssistedFactory
    interface Factory {
        fun create(server: Server): RuntimePlayerRegistry
    }

    private val playerFactory = playerFactoryFactory.create(server)

    override fun get(id: String): Player? {
        TODO("Not yet implemented")
    }

    override fun getByUUID(uuid: UUID): Player? {
        TODO("Not yet implemented")
    }

    override fun create(uuid: UUID): Player  = playerFactory.create(uuid)

    override val components: PlayerComponentRegistry
        get() = TODO("Not yet implemented")
}
