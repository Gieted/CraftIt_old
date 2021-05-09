package org.craftit.api.resources.entities.player

import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import org.craftit.api.resources.commands.CommandParser

class VanillaInputResolver @AssistedInject constructor(
    @Assisted private val player: Player,
    private val commandParser: CommandParser,
) : InputResolver {
    @AssistedFactory
    interface Factory {
        fun create(player: Player): VanillaInputResolver
    }

    override fun onChat(message: String) {
        fun String.isCommand() = startsWith("/")

        if (message.isCommand()) {
            val parsingResult = commandParser.parse(message.drop(1))
            val command = player.server.commands[parsingResult.id]
            if (command == null) {
                player.sendMessage("No such command: ${parsingResult.id}")
                return
            }
            player.controller.executeCommand(command, parsingResult.arguments)
        }
    }
}
