package org.craftit.api

import org.craftit.api.resources.commands.Command

abstract class QuickPlugin(private val craftIt: CraftIt) : Plugin {
    inner class Commands {
        operator fun String.invoke(factory: (id: String) -> Command) {
            craftIt.requestRegisteringCommand(this, factory)
        }
    }

    abstract fun Commands.register()

    override fun enable() {
        Commands().register()
    }
}
