package org.craftit.runtime.resources.commands

import org.craftit.api.resources.Resource
import org.craftit.api.resources.commands.Command

data class CommandPrototype(override val id: String, val factory: Command.Factory): Resource
