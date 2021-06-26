package org.craftit.runtime.resources.entities

import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import org.craftit.api.Server
import org.craftit.api.resources.entities.Entity
import org.craftit.api.resources.entities.EntityRegistry
import org.craftit.runtime.resources.entities.player.RuntimePlayerRegistry
import javax.inject.Inject

class RuntimeEntityRegistry @AssistedInject constructor(
    playerRegistryFactory: RuntimePlayerRegistry.Factory,
    @Assisted server: Server
) : EntityRegistry {

    @AssistedFactory
    interface Factory {
        fun create(server: Server): RuntimeEntityRegistry
    }

    override val players: RuntimePlayerRegistry = playerRegistryFactory.create(server)

    override fun get(id: String): Entity? = TODO()
}
