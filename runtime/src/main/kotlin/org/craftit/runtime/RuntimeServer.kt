package org.craftit.runtime

import org.craftit.api.Server
import org.craftit.runtime.resources.commands.RuntimeCommandRegistry
import org.craftit.runtime.resources.entities.RuntimeEntityRegistry
import javax.inject.Inject

class RuntimeServer @Inject constructor(
    override val commands: RuntimeCommandRegistry,
    entityRegistryFactory: RuntimeEntityRegistry.Factory
) : Server {
    override val entities: RuntimeEntityRegistry = entityRegistryFactory.create(this)
}
