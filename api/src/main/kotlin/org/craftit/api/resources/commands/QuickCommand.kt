package org.craftit.api.resources.commands

import org.craftit.api.resources.commands.argument_parsers.ArgumentParser
import org.craftit.api.resources.commands.argument_parsers.EntitiesParser
import org.craftit.api.resources.commands.argument_parsers.IntParser
import org.craftit.api.resources.commands.argument_parsers.OptionParser
import org.craftit.api.resources.commands.parameters.EntityParameter
import org.craftit.api.resources.commands.parameters.NumericParameter
import org.craftit.api.resources.commands.parameters.OptionParameter
import org.craftit.api.resources.commands.parameters.Parameter
import org.craftit.api.resources.entities.Entity
import java.lang.Exception
import kotlin.reflect.KProperty


@Suppress("UNCHECKED_CAST")
abstract class QuickCommand : Command {
    @DslMarker
    private annotation class CommandMarker

    @CommandMarker
    data class ExecutionScope(val issuer: CommandIssuer)

    @CommandMarker
    inner class Command constructor(
        val issuer: CommandIssuer,
    ) {
        var executors = mutableMapOf<CommandDefinition.CommandVariation, (ExecutionScope.() -> Unit)>()
        var variations =
            listOf(CommandDefinition.CommandVariation(emptyList()))

        var parsers = mutableMapOf<Parameter, ArgumentParser<*>>()

        var argumentValues: Map<Parameter, Any>? = null

        private operator fun CommandDefinition.CommandVariation.plus(parameter: Parameter) =
            CommandDefinition.CommandVariation(parameters + parameter)

        private operator fun CommandDefinition.CommandVariation.plus(other: CommandDefinition.CommandVariation) =
            CommandDefinition.CommandVariation(parameters + other.parameters)

        abstract inner class Argument<T> {
            fun registerParameter(parameter: Parameter, optional: Boolean, parser: ArgumentParser<*>?) {
                variations = if (optional) {
                    variations.flatMap { listOf(it, it + parameter) }.toMutableList()
                } else {
                    variations.map { it + parameter }.toMutableList()
                }

                if (parser != null) {
                    parsers[parameter] = parser
                }
            }

            protected var parameter: Parameter? = null

            operator fun getValue(thisRef: Any?, prop: KProperty<*>): T = argumentValues!![parameter] as T
        }

        inner class IntArgument<T : Int?>(
            private val name: String? = null,
            private val optional: Boolean,
            private val min: Int = Int.MIN_VALUE,
            private val max: Int = Int.MAX_VALUE,
            private val parser: ArgumentParser<Int>?
        ) : Argument<T>() {
            operator fun provideDelegate(thisRef: Any?, prop: KProperty<*>): IntArgument<T> {
                parameter = NumericParameter(name ?: prop.name, min, max, Int::class)
                registerParameter(parameter!!, optional, parser)

                return this
            }
        }

        inner class EntityArgument<T>(
            private val name: String? = null,
            private val optional: Boolean,
            private val multiple: Boolean,
            private val playerOnly: Boolean,
            private val parser: ArgumentParser<*>?
        ) : Argument<T>() {
            operator fun provideDelegate(thisRef: Any?, prop: KProperty<*>): EntityArgument<T> {
                parameter = EntityParameter(name ?: prop.name, multiple, playerOnly)
                registerParameter(parameter!!, optional, parser)

                return this
            }
        }

        fun intArgument(
            name: String? = null,
            optional: Boolean = false,
            min: Int = Int.MIN_VALUE,
            max: Int = Int.MAX_VALUE,
            parser: ArgumentParser<Int> = IntParser()
        ) = IntArgument<Int?>(name, optional, min, max, parser)

        fun intArgument(
            name: String? = null,
            min: Int = Int.MIN_VALUE,
            max: Int = Int.MAX_VALUE,
            parser: ArgumentParser<Int> = IntParser()
        ) = IntArgument<Int>(name, false, min, max, parser)

        fun entitiesArgument(name: String? = null, parser: ArgumentParser<List<Entity>> = EntitiesParser()) =
            EntityArgument<List<Entity>>(name, optional = false, multiple = true, playerOnly = false, parser)

        fun entitiesArgument(
            name: String? = null,
            optional: Boolean,
            parser: ArgumentParser<List<Entity>> = EntitiesParser()
        ) = EntityArgument<List<Entity>?>(
            name, optional,
            multiple = true,
            playerOnly = false,
            parser
        )

        fun execute(executor: ExecutionScope.() -> Unit) {
            this.executors[variations.last()] = executor
        }

        fun option(name: String, define: Command.() -> Unit) {
            val subcommand = Command(issuer)
            subcommand.define()

            val optionParameter = OptionParameter(name)

            this.parsers[optionParameter] = OptionParser(name)

            variations = variations.flatMap {
                if (it.parameters.isEmpty() || it.parameters.last() !is OptionParameter) listOf(it + optionParameter)
                else listOf(
                    it,
                    CommandDefinition.CommandVariation(it.parameters.dropLast(1) + optionParameter)
                )
            }
            
            this.executors.putAll(subcommand.executors.mapKeys { variations.last() + it.key })
        }
    }

    protected abstract fun Command.define()

    override fun getDefinition(issuer: CommandIssuer): CommandDefinition {
        val command = Command(issuer)
        command.define()

        return CommandDefinition(command.variations)
    }

    override fun execute(issuer: CommandIssuer, arguments: String) {
        val command = Command(issuer)
        command.define()
        command.variations.forEach { variation ->
            val argumentsIterator = arguments.iterator()
            try {
                val argumentValues =
                    variation.parameters.associateWith { command.parsers[it]!!.parse(argumentsIterator, issuer) }

                command.argumentValues = argumentValues
                command.executors[variation]?.invoke(ExecutionScope(issuer))
            } catch (ignored: Exception) {
            }
        }
    }

    override fun getSuggestions(
        issuer: CommandIssuer,
        currentArguments: String
    ): org.craftit.api.resources.commands.Command.Suggestions {
        TODO("Not yet implemented")
    }
}
