package org.craftit.runtime.resources.plugin

import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import org.craftit.api.CraftIt
import org.craftit.api.resources.plugin.Plugin
import org.craftit.api.resources.commands.Command

class QuickPlugin @AssistedInject constructor(
    @Assisted private val craftIt: CraftIt,
    @Assisted private val commands: CraftIt.RegisterCommands.() -> Unit,
    @Assisted override val id: String,
) : Plugin {

    @AssistedFactory
    interface Factory {
        fun create(craftIt: CraftIt, commands: CraftIt.RegisterCommands.() -> Unit, id: String): QuickPlugin
    }

    inner class RegisterCommandsImpl : CraftIt.RegisterCommands {
        override fun String.invoke(factory: Command.Factory) {
            craftIt.commands.register(this, factory)
        }
    }

    override fun enable() {
        RegisterCommandsImpl().commands()
    }
}
