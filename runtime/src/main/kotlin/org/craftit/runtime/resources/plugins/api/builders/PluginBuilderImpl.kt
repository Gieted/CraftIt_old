package org.craftit.runtime.resources.plugins.api.builders

import org.craftit.api.CraftIt
import org.craftit.api.builders.PluginBuilder
import org.craftit.api.resources.commands.Command
import org.craftit.api.resources.plugins.Plugin
import javax.inject.Inject

class PluginBuilderImpl @Inject constructor(private val craftIt: CraftIt) : PluginBuilder {
    
    private inner class PluginImpl(private val commandConfigurations: List<PluginBuilder.RegisterCommands.() -> Unit>) : Plugin {
        override val id: String
            get() = "undefined"

        private inner class RegisterCommandsImpl : PluginBuilder.RegisterCommands {
            override fun String.invoke(factory: Command.Factory) {
                craftIt.commands.register(this, factory)
            }
        }

        override fun enable() {
            val registerCommands = RegisterCommandsImpl()
            commandConfigurations.forEach { configure -> registerCommands.configure() }
        }
    }
    
    private val commandConfigurations = mutableListOf<PluginBuilder.RegisterCommands.() -> Unit>()

    override fun commands(configure: PluginBuilder.RegisterCommands.() -> Unit) {
        commandConfigurations.add(configure)
    }

    fun build(): Plugin = PluginImpl(commandConfigurations)
}
