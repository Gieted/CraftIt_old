package org.craftit.runtime

import org.craftit.api.CraftIt
import org.craftit.api.Plugin
import org.craftit.api.resources.commands.Command

class QuickPlugin(private val craftIt: CraftIt, private val commands: CraftIt.RegisterCommands.() -> Unit) : Plugin {

    inner class RegisterCommandsImpl : CraftIt.RegisterCommands {
        override fun String.invoke(factory: Command.Factory) {
            craftIt.commands.register(this, factory)
        }
    }
    
    override fun enable() {
        RegisterCommandsImpl().commands()
    }
}
