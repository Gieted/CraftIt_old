package org.craftit.api.resources.commands

import javax.inject.Inject

class VanillaCommandParser @Inject constructor() : CommandParser {
    override fun parse(commandString: String): CommandParser.Result {
        val idAndArguments = commandString.split(" ")

        return CommandParser.Result(idAndArguments.first(), idAndArguments.drop(1).joinToString(" "))
    }
}
