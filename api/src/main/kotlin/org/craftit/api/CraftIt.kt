package org.craftit.api

import com.mojang.brigadier.tree.ArgumentCommandNode
import com.mojang.brigadier.tree.CommandNode
import com.mojang.brigadier.tree.LiteralCommandNode
import org.craftit.api.resources.IdGenerator
import org.craftit.api.resources.plugin.Plugin
import org.craftit.api.resources.commands.Command
import org.craftit.api.resources.commands.CommandBuilder
import org.craftit.api.resources.commands.parameters.EntityParameter
import org.craftit.api.resources.commands.parameters.NumericParameter
import org.craftit.api.resources.commands.parameters.OptionParameter
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

    fun command(configure: CommandBuilder.() -> Unit): Command

    fun <T, Y> EntityParameter.toBrigadierCommandNode(): ArgumentCommandNode<T, Y>

    fun <T> NumericParameter<Int>.toBrigadierCommandNode(): ArgumentCommandNode<T, Int>

    fun <T> OptionParameter.toBrigadierCommandNode(): LiteralCommandNode<T>
}
