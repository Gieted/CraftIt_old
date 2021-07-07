package org.craftit.runtime.resources.plugin

import com.mojang.brigadier.arguments.IntegerArgumentType
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.builder.RequiredArgumentBuilder
import com.mojang.brigadier.tree.ArgumentCommandNode
import com.mojang.brigadier.tree.LiteralCommandNode
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import org.craftit.api.CraftIt
import org.craftit.api.Server
import org.craftit.api.resources.IdGenerator
import org.craftit.api.resources.commands.Command
import org.craftit.api.resources.commands.CommandBuilder
import org.craftit.api.resources.commands.parameters.EntityParameter
import org.craftit.api.resources.commands.parameters.NumericParameter
import org.craftit.api.resources.commands.parameters.OptionParameter
import org.craftit.api.resources.entities.player.Player
import org.craftit.api.resources.plugin.Plugin
import org.craftit.runtime.resources.commands.EntityArgumentWrapper
import org.craftit.runtime.resources.commands.ParameterConverter
import org.craftit.runtime.resources.commands.QuickCommand
import org.craftit.runtime.resources.entities.player.VanillaPlayer

class PluginApi @AssistedInject constructor(
    @Assisted private val server: Server,
    @Assisted override val pluginId: String,
    override val idGenerator: IdGenerator,
    private val vanillaPlayerFactory: VanillaPlayer.Factory,
    private val quickPluginFactory: QuickPlugin.Factory,
    private val quickCommandFactory: QuickCommand.Factory,
    private val parameterConverter: ParameterConverter
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
            override fun vanilla(id: String): Player = vanillaPlayerFactory.create(id)
        }
    }

    override fun plugin(commands: CraftIt.RegisterCommands.() -> Unit): Plugin =
        quickPluginFactory.create(this, commands, pluginId)

    override fun command(configure: CommandBuilder.() -> Unit): Command = quickCommandFactory.create(configure)

    override fun <T, Y> EntityParameter.toBrigadierCommandNode(): ArgumentCommandNode<T, Y> =
        with(parameterConverter) { this@toBrigadierCommandNode.toBrigadierCommandNode() }

    override fun <T> NumericParameter<Int>.toBrigadierCommandNode(): ArgumentCommandNode<T, Int> =
        with(parameterConverter) { this@toBrigadierCommandNode.toBrigadierCommandNode() }

    override fun <T> OptionParameter.toBrigadierCommandNode(): LiteralCommandNode<T> =
        with(parameterConverter) { this@toBrigadierCommandNode.toBrigadierCommandNode() }
}
