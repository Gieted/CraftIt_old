package org.craftit.runtime

import org.craftit.api.Server
import org.craftit.runtime.resources.commands.CommandMapRegistry
import javax.inject.Inject

class PluggableServer @Inject constructor(commandMapRegistry: CommandMapRegistry) : Server {
    override val commands = commandMapRegistry
}
