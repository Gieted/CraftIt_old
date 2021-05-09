package org.craftit.runtime

import org.craftit.api.CraftIt
import org.craftit.api.Server
import org.craftit.api.resources.commands.Command
import javax.inject.Inject

class CraftItApi @Inject constructor(private val server: Server) : CraftIt {
    override fun requestRegisteringCommand(defaultId: String, factory: (id: String) -> Command) {
        server.commands[defaultId] = factory(defaultId)
    }
}
