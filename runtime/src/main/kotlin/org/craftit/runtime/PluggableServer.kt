package org.craftit.runtime

import org.craftit.api.Server
import org.craftit.api.resources.commands.Command
import org.craftit.runtime.resources.SingletonMapRegistry
import javax.inject.Inject

class PluggableServer @Inject constructor() : Server {
    override val commands = SingletonMapRegistry<Command>()
}
