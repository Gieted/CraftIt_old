package org.craftit.api.resources.entities.player

import org.craftit.api.resources.commands.Command

interface PlayerController {
    fun executeCommand(command: Command, arguments: String)
}
