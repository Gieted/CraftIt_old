package org.craftit.runtime.resources.entities

import org.craftit.api.Server
import org.craftit.api.resources.entities.Entity
import org.craftit.api.resources.entities.EntityRegistry
import org.craftit.runtime.resources.entities.player.RuntimePlayerRegistry
import javax.inject.Inject

class VanillaEntityRegistry @Inject constructor(
    playerRegistryFactory: RuntimePlayerRegistry.Factory,
    server: Server
) : EntityRegistry {

    override val players: RuntimePlayerRegistry = playerRegistryFactory.create(server)

    override fun get(id: String): Entity? = TODO()
}
