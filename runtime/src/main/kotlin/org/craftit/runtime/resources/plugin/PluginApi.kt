package org.craftit.runtime.resources.plugin

import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import org.craftit.api.CraftIt
import org.craftit.api.Server
import org.craftit.api.resources.plugin.Plugin
import org.craftit.api.resources.IdGenerator
import org.craftit.api.resources.commands.Command
import org.craftit.api.resources.entities.player.Player
import org.craftit.runtime.resources.entities.player.VanillaPlayer
import java.util.*

class PluginApi @AssistedInject constructor(
    @Assisted private val server: Server,
    @Assisted override val pluginId: String,
    override val idGenerator: IdGenerator,
    private val vanillaPlayerFactory: VanillaPlayer.Factory,
    private val quickPluginFactory: QuickPlugin.Factory
) : CraftIt {

    @AssistedFactory
    interface Factory {
        fun create(server: Server, pluginId: String): PluginApi
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
            override fun vanilla(uuid: UUID): Player = vanillaPlayerFactory.create(uuid)
        }
    }

    override fun plugin(commands: CraftIt.RegisterCommands.() -> Unit): Plugin =
        quickPluginFactory.create(this, commands, pluginId)
}
