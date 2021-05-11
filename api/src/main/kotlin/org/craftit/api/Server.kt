package org.craftit.api

import org.craftit.api.resources.SingletonRegistry
import org.craftit.api.resources.commands.Command
import org.craftit.api.resources.commands.CommandRegistry

interface Server {
    val commands: CommandRegistry
}
