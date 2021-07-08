package org.craftit.runtime.resources.commands

import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import org.craftit.api.resources.commands.Command
import org.craftit.api.resources.commands.CommandBuilder
import org.craftit.api.resources.commands.CommandDefinition
import org.craftit.api.resources.commands.CommandIssuer


class QuickCommand @AssistedInject constructor(
    @Assisted val configure: CommandBuilder.() -> Unit,
    private val commandBuilderFactory: CommandBuilderImpl.Factory
) : Command {

    @AssistedFactory
    interface Factory {
        fun create(configure: CommandBuilder.() -> Unit): QuickCommand
    }

    override val id: String
        get() = "craftit:command"

    override val state: Any
        get() = Any()
    
    override fun getDefinition(issuer: CommandIssuer): CommandDefinition {
        val commandBuilder = commandBuilderFactory.create(issuer)
        commandBuilder.configure()
        
        return commandBuilder.definition
    }

    override fun execute(issuer: CommandIssuer, arguments: String) {
        val commandBuilder = commandBuilderFactory.create(issuer)
        commandBuilder.configure()
    }

    override fun getSuggestions(issuer: CommandIssuer, currentArguments: String): Command.Suggestions {
        TODO("Not yet implemented")
    }
}
