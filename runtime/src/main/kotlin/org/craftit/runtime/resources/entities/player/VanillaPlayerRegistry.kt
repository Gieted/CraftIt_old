package org.craftit.runtime.resources.entities.player

import org.craftit.api.resources.entities.PlayerPrototype
import org.craftit.api.resources.entities.player.OnlinePlayerRegistry
import org.craftit.api.resources.entities.player.Player
import org.craftit.api.resources.entities.player.PlayerRegistry
import org.craftit.api.resources.entities.player.components.PlayerComponentsRegistry
import org.craftit.runtime.server.ServerScope
import javax.inject.Inject

@ServerScope
class VanillaPlayerRegistry @Inject constructor(
    override val components: PlayerComponentsRegistry,
    override var factory: Player.Factory
) : PlayerRegistry, Player.Factory {
    
    private val players = mutableListOf<Player>()

    override fun get(id: String): Player? = players.find { it.id == id }

    override fun create(id: String): Player = factory.create(id).also { players.add(it) }

    override val online: OnlinePlayerRegistry
        get() = TODO("Not yet implemented")
    
    override var prototype = PlayerPrototype()

}
