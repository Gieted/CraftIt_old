package org.craftit.api

import org.craftit.api.resources.IdGenerator
import org.craftit.api.resources.commands.Command
import org.craftit.api.resources.entities.player.Player

interface CraftIt {
    val commands: Commands
    val idGenerator: IdGenerator
    val entities: Entities

    fun plugin(commands: RegisterCommands.() -> Unit): Plugin

    interface RegisterCommands {
        operator fun String.invoke(factory: Command.Factory)
    }

    interface Commands {
        fun register(defaultId: String, factory: Command.Factory)
    }

    interface Entities {
        val player: PlayerPrototype

        interface PlayerPrototype {
            fun vanilla(): Player
        }
    }
}
