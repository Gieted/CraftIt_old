package org.craftit.runtime.resources.plugins.api

import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import org.craftit.api.CraftIt
import org.craftit.api.Server
import org.craftit.api.resources.IdGenerator
import org.craftit.api.resources.commands.Command
import org.craftit.api.resources.commands.CommandBuilder
import org.craftit.api.resources.commands.parameters.Parameter
import org.craftit.api.builders.ParametersBuilder
import org.craftit.api.builders.PluginBuilder
import org.craftit.api.resources.entities.player.Player
import org.craftit.api.resources.plugins.Plugin
import org.craftit.runtime.resources.commands.QuickCommand
import org.craftit.runtime.resources.plugins.api.builders.ParametersBuilderImpl
import org.craftit.runtime.resources.plugins.api.builders.PluginBuilderImpl
import org.craftit.runtime.resources.entities.player.VanillaPlayer
import javax.inject.Provider

class PluginApi @AssistedInject constructor(
    @Assisted private val server: Server,
    @Assisted override val pluginId: String,
    override val idGenerator: IdGenerator,
    private val vanillaPlayerFactory: VanillaPlayer.Factory,
    private val quickCommandFactory: QuickCommand.Factory,
    private val parametersBuilderProvider: Provider<ParametersBuilderImpl>,
    private val pluginComponentFactory: PluginComponent.Factory
) : CraftIt {

    @AssistedFactory
    interface Factory {
        fun create(server: Server, pluginId: String): PluginApi
    }

    private val pluginBuilderProvider: Provider<PluginBuilderImpl>
    
    init {
        val component = pluginComponentFactory.create(this)
        
        pluginBuilderProvider = component.pluginBuilderProvider()
    }

    override val commands = CommandsApi()

    inner class CommandsApi : CraftIt.Commands {
        override fun register(defaultId: String, factory: Command.Factory) {
            server.commands.add(factory.create(defaultId))
        }
    }

    override val entities = EntitiesApi()


    inner class EntitiesApi : CraftIt.Entities {
        override val player = PlayerApi()

        inner class PlayerApi : CraftIt.Entities.Player {
            override fun vanilla(id: String): Player = vanillaPlayerFactory.create(id)
        }
    }

    override fun plugin(configure: PluginBuilder.() -> Unit): Plugin {
        val pluginBuilder = pluginBuilderProvider.get()
        pluginBuilder.configure()
        
        return pluginBuilder.build()
    }

    override fun command(configure: CommandBuilder.() -> Unit): Command = quickCommandFactory.create(configure)
    
    override fun parameters(configure: ParametersBuilder.() -> Unit): List<Parameter> {
        val parameterBuilder =  parametersBuilderProvider.get()
        parameterBuilder.configure()
        
        return parameterBuilder.build()
    }
}
