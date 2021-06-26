package org.craftit.runtime

import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import org.craftit.api.CraftIt
import org.craftit.api.Plugin
import org.craftit.api.resources.commands.Command
import org.craftit.runtime.resources.commands.CommandPrototype

class PluginApi @AssistedInject constructor(@Assisted private val server: RuntimeServer) : CraftIt {

    @AssistedFactory
    interface Factory {
        fun create(server: RuntimeServer): PluginApi
    }
    
    override val commands: CraftIt.Commands = CommandsApi()

    inner class CommandsApi : CraftIt.Commands {
        override fun register(defaultId: String, factory: Command.Factory) {
            server.commands.prototypes[defaultId] = CommandPrototype(defaultId, factory)
        }
    }

    override fun plugin(commands: CraftIt.RegisterCommands.() -> Unit): Plugin = QuickPlugin(this, commands)
}
