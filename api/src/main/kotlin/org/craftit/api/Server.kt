package org.craftit.api

import org.craftit.api.resources.commands.CommandRegistry
import org.craftit.api.resources.entities.EntityRegistry

interface Server {
    val commands: CommandRegistry
    val entities: EntityRegistry
}
