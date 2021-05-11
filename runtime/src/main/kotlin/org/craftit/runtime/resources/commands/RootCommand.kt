package org.craftit.runtime.resources.commands

import org.craftit.api.resources.commands.Command
import org.craftit.api.resources.commands.CommandDefinition
import org.craftit.api.resources.commands.CommandIssuer
import org.craftit.api.resources.commands.parameters.OptionParameter
import org.craftit.api.resources.entities.player.Player
import javax.inject.Inject

class RootCommand @Inject constructor() : Command {
    override val id: String = "craftit:root"

    override fun getDefinition(issuer: CommandIssuer): CommandDefinition =
        CommandDefinition((issuer as Player).server.commands.flatMap { command ->
            command.getDefinition(issuer).variations.map {
                OptionParameter(
                    command.id
                ) + it
            }
        })

    override fun execute(issuer: CommandIssuer, arguments: String) {
        val id = arguments.split(' ').first()
        val args = arguments.split(' ').drop(1).joinToString(" ")
        val command = (issuer as Player).server.commands[id]
        if (command == null) {
            issuer.sendMessage("Cannot find command: $id")
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
