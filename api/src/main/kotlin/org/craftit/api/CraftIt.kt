package org.craftit.api

import org.craftit.api.resources.commands.Command

interface CraftIt {
    fun requestRegisteringCommand(defaultId: String, factory: (id: String) -> Command)
}
