package org.craftit.api

import org.craftit.api.resources.SingletonRegistry
import org.craftit.api.resources.commands.Command

interface Server {
    val commands: SingletonRegistry<Command>
}
