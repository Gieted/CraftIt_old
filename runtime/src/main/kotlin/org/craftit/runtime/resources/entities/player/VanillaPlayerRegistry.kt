package org.craftit.runtime.resources.entities.player

import org.craftit.api.resources.entities.player.Player
import org.craftit.api.resources.entities.player.PlayerRegistry
import org.craftit.api.resources.entities.player.components.PlayerComponentRegistry
import java.util.*
import javax.inject.Inject

class VanillaPlayerRegistry @Inject constructor(
    private val nativePlayerFactory: NativePlayerImpl.Factory,
    private val nativeConnectorFactory: NativeConnectorImpl.Factory,
    private val playerFactory: PlayerFactory
) : PlayerRegistry {
    
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
