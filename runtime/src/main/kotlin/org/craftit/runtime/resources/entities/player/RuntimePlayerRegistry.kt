package org.craftit.runtime.resources.entities.player

import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import org.craftit.api.Server
import org.craftit.api.resources.entities.player.Player
import org.craftit.api.resources.entities.player.PlayerRegistry
import org.craftit.api.resources.entities.player.components.OnlineComponent
import org.craftit.api.resources.entities.player.components.PlayerComponentRegistry
import java.util.*

class RuntimePlayerRegistry @AssistedInject constructor(
    private val nativePlayerFactory: NativePlayerImpl.Factory,
    private val nativeConnectorFactory: NativeConnectorImpl.Factory,
    private val onlineComponentFactory: OnlineComponent.Factory,
    @Assisted server: Server,
    playerFactoryFactory: PlayerFactory.Factory
) : PlayerRegistry {

    @AssistedFactory
    interface Factory {
        fun create(server: Server): RuntimePlayerRegistry
    }
    
    private val entries = mutableSetOf<Entry>()
    private val playerFactory = playerFactoryFactory.create(server)

    data class Entry(
        val player: Player,
        val nativeConnector: NativeConnectorImpl? = null,
        val serverPlayerEntity: Any? = null
    )

    override fun get(id: String): Player? = entries.find { it.player.id == id }?.player

    override fun getByUUID(uuid: UUID): Player? = entries.find { it.player.uuid == uuid }?.player

    override val components: PlayerComponentRegistry
        get() = TODO("Not yet implemented")

    override fun create(uuid: UUID): Player = playerFactory.create(uuid)

    fun getOrCreate(uuid: UUID, serverPlayerEntity: Any, playNetHandler: Any): Entry =
        entries.find { it.player.uuid == uuid } ?: run {
            val player = playerFactory.create(uuid)
            val onlineComponent = onlineComponentFactory.create(
                player,
                nativePlayerFactory.create(serverPlayerEntity, nativeConnectorFactory.create(playNetHandler))
            )
            player.components.add(onlineComponent)

            val entry = Entry(player)

            entries.add(entry)

            entry
        }
}
