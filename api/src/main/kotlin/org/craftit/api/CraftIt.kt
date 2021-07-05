package org.craftit.api

import org.craftit.api.resources.IdGenerator
import org.craftit.api.resources.plugin.Plugin
import org.craftit.api.resources.commands.Command
import java.util.*

interface CraftIt {
    val commands: Commands
    val idGenerator: IdGenerator
    val entities: Entities
    val pluginId: String

    fun plugin(commands: RegisterCommands.() -> Unit): Plugin

    interface RegisterCommands {
        operator fun String.invoke(factory: Command.Factory)
    }

    interface Commands {
        fun register(defaultId: String, factory: Command.Factory)
    }

    interface Entities {
        val player: Player

        interface Player {
            fun vanilla(id: String): org.craftit.api.resources.entities.player.Player
        }
    }
}
