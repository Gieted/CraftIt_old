package org.craftit.test_plugin.commands

import org.craftit.api.CraftIt
import org.craftit.api.resources.commands.Command
import org.craftit.api.resources.commands.CommandDefinition
import org.craftit.api.resources.commands.CommandIssuer

class Test(override val id: String, private val craftIt: CraftIt) : Command {
    override fun getDefinition(issuer: CommandIssuer): CommandDefinition = CommandDefinition(craftIt.parameters {
        int("x") {
            it()
        }
    })

    override fun execute(issuer: CommandIssuer, arguments: String) {
        TODO("Not yet implemented")
    }

    override fun getSuggestions(issuer: CommandIssuer, currentArguments: String): Command.Suggestions {
        TODO("Not yet implemented")
    }
}
