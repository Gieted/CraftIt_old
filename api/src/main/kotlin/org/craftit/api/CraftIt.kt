package org.craftit.api

import org.craftit.api.resources.IdGenerator
import org.craftit.api.resources.commands.Command
import org.craftit.api.resources.commands.CommandBuilder
import org.craftit.api.resources.commands.parameters.Parameter
import org.craftit.api.builders.PluginBuilder
import org.craftit.api.resources.plugins.Plugin

interface CraftIt {
    val commands: Commands
    val idGenerator: IdGenerator
    val entities: Entities
    val pluginId: String

    fun plugin(configure: PluginBuilder.() -> Unit): Plugin

    interface Commands {
        fun register(defaultId: String, factory: Command.Factory)
    }

    interface Entities {
        val player: Player

        interface Player {
            fun vanilla(id: String): org.craftit.api.resources.entities.player.Player
        }
    }

    fun command(configure: CommandBuilder.() -> Unit): Command
}
