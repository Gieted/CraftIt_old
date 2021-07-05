package org.craftit.runtime.resources.entities

import org.craftit.api.resources.entities.Entity
import org.craftit.api.resources.entities.EntityRegistry
import org.craftit.api.resources.entities.player.PlayerRegistry
import org.craftit.runtime.server.ServerScope
import javax.inject.Inject

@ServerScope
class VanillaEntityRegistry @Inject constructor(
    override val players: PlayerRegistry
) : EntityRegistry {
    
    override fun get(id: String): Entity? = TODO()
}
