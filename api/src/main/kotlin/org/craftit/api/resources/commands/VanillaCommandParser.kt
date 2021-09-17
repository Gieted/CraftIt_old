package org.craftit.api.resources.commands

class VanillaCommandParser : CommandParser {
    override fun parse(commandString: String): CommandParser.Result {
        val idAndArguments = commandString.split(" ")

        return CommandParser.Result(idAndArguments.first(), idAndArguments.drop(1).joinToString(" "))
    }
}
