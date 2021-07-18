package org.craftit.api.builders

import org.craftit.api.resources.commands.Command

interface PluginBuilder {
    fun commands(configure: RegisterCommands.() -> Unit)
    
    interface RegisterCommands {
        operator fun String.invoke(factory: Command.Factory)
    }
}
