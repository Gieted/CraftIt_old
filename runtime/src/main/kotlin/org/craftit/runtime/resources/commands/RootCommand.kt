package org.craftit.runtime.resources.commands

import org.craftit.api.resources.commands.Command
import org.craftit.api.resources.commands.CommandDefinition
import org.craftit.api.resources.commands.CommandIssuer
import org.craftit.api.resources.entities.player.Player
import javax.inject.Inject
import javax.inject.Provider

class RootCommand @Inject constructor() :
    Command {

    override val id: String
        get() = "craftit:root"

    override val state: Any
        get() = Any()

    override fun getDefinition(issuer: CommandIssuer): CommandDefinition =
        TODO()

    override fun execute(issuer: CommandIssuer, arguments: String) {
        val id = arguments.split(' ').first()
        val args = arguments.split(' ').drop(1).joinToString(" ")

        if (id.isEmpty()) {
            return
        }

        val command = (issuer as Player).server.commands[id]
        if (command == null) {
            issuer.sendErrorMessage("No such command: $id")
        } else {
            command.execute(issuer, args)
        }
    }

    override fun getSuggestions(issuer: CommandIssuer, currentArguments: String): Command.Suggestions {
        val id = currentArguments.split(' ').first()
        val args = currentArguments.split(' ').drop(1).joinToString(" ")
        val command = (issuer as Player).server.commands[id]

        return command?.getSuggestions(issuer, args) ?: TODO()
    }
}
