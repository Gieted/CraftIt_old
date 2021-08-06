package org.craftit.runtime.resources.plugins.api.builders

import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import org.craftit.api.builders.ParametersBuilder
import org.craftit.api.resources.commands.Command
import org.craftit.api.resources.commands.CommandBuilder
import org.craftit.api.resources.commands.CommandDefinition
import org.craftit.api.resources.commands.CommandIssuer
import org.craftit.api.resources.commands.arguments.Argument
import org.craftit.api.resources.commands.arguments.NotNullArgument
import org.craftit.api.resources.commands.parsing.ArgumentParser
import org.craftit.api.resources.commands.parsing.ParsingException
import org.craftit.api.resources.commands.parsing.StringReader
import org.craftit.api.resources.entities.Entity
import org.craftit.runtime.resources.commands.StringReaderImpl
import javax.inject.Provider
import kotlin.reflect.KProperty

@Suppress("UNCHECKED_CAST")
class CommandBuilderImpl @AssistedInject constructor(
    @Assisted override val issuer: CommandIssuer,
    private val commandBuilderFactory: Factory,
    private val parameterBuilderProvider: Provider<ParametersBuilderImpl>,
    private val stringReaderFactory: StringReaderImpl.Factory
) : CommandBuilder {

    class Wrapper @AssistedInject constructor(
        @Assisted val configure: CommandBuilder.() -> Unit,
        private val commandBuilderFactory: CommandBuilderImpl.Factory
    ) : Command {

        @AssistedFactory
        interface Factory {
            fun create(configure: CommandBuilder.() -> Unit): Wrapper
        }

        override val id: String
            get() = "undefined"

        override val state: Any
            get() = Any()

        override fun getDefinition(issuer: CommandIssuer): CommandDefinition {
            val commandBuilder = commandBuilderFactory.create(issuer)
            commandBuilder.configure()

            return commandBuilder.definition
        }

        override fun execute(issuer: CommandIssuer, arguments: String) {
            val commandBuilder = commandBuilderFactory.create(issuer)
            commandBuilder.configure()

            commandBuilder.executeCommand(issuer, arguments)
        }

        override fun getSuggestions(issuer: CommandIssuer, currentArguments: String): Command.Suggestions {
            TODO("Not yet implemented")
        }
    }

    @AssistedFactory
    fun interface Factory {
        fun create(issuer: CommandIssuer): CommandBuilderImpl
    }

    private abstract inner class ArgumentImpl<T : Any> : Argument<T> {
        abstract val name: String
        abstract val optional: Boolean
        val children = mutableListOf<ArgumentImpl<*>>()
        var parent: ArgumentImpl<*>? = null
            private set

        override operator fun getValue(thisRef: Any?, prop: KProperty<*>): T? {
            return value
        }

        fun addChild(argument: ArgumentImpl<*>) {
            children.add(argument)
            argument.parent = this
        }

        abstract fun ParametersBuilder.parameter()

        var mutableParser: ArgumentParser<T>? = null
        override val parser
            get() = mutableParser!!

        var executor: (() -> Unit)? = null

        var value: T? = null

        fun parseRecursively(reader: StringReader, issuer: CommandIssuer): Boolean {
            var success = true

            try {
                value = parser.parse(reader, issuer)
            } catch (exception: ParsingException) {
                success = false
            }

            children.forEach {
                val result = it.parseRecursively(reader.clone(), issuer)
                if (!result && !it.optional) {
                    success = false
                }
            }

            return success
        }

        val furthestExecutableChild:
    }

    private open inner class IntArgument(
        override val name: String,
        override val optional: Boolean,
        val min: Int,
        val max: Int
    ) : ArgumentImpl<Int>() {
        override fun ParametersBuilder.parameter() {
            int(name, optional, min, max) {
                children.forEach { it.apply { parameter() } }
            }
        }
    }

    private inner class NotNullIntArgument(
        name: String,
        min: Int,
        max: Int
    ) : IntArgument(name, false, min, max), NotNullArgument<Int> {

        override fun getValue(thisRef: Any?, prop: KProperty<*>): Int {
            return super.getValue(thisRef, prop)!!
        }
    }

    private open inner class EntitiesArgument(
        override val name: String,
        override val optional: Boolean,
        val playerOnly: Boolean,
    ) : ArgumentImpl<Set<Entity>>() {
        override fun ParametersBuilder.parameter() {
            entity(name, optional, true, playerOnly) {
                children.forEach { it.apply { parameter() } }
            }
        }
    }

    private inner class NotNullEntitiesArgument(
        name: String,
        playerOnly: Boolean,
    ) : EntitiesArgument(name, false, playerOnly), NotNullArgument<Set<Entity>> {

        override fun getValue(thisRef: Any?, prop: KProperty<*>): Set<Entity> {
            return super.getValue(thisRef, prop)!!
        }
    }

    private inner class OptionArgument(
        override val name: String,
        override val optional: Boolean,
    ) : ArgumentImpl<String>() {
        override fun ParametersBuilder.parameter() {
            option(name, optional) {
                children.forEach { it.apply { parameter() } }
            }
        }
    }

    private var lastArgument: ArgumentImpl<*>? = null
    private val rootArguments = mutableListOf<ArgumentImpl<*>>()
    private var rootExecutor: (() -> Unit)? = null

    val definition: CommandDefinition
        get() = CommandDefinition((parameterBuilderProvider.get()){
            rootArguments.forEach { it.apply { parameter() } }
        })

    private fun addArgument(parameter: ArgumentImpl<*>) {
        val lastArgument = lastArgument
        if (lastArgument == null) {
            rootArguments.add(parameter)
            this.lastArgument = parameter
        } else {
            lastArgument.addChild(parameter)
            this.lastArgument = parameter
        }
    }

    override fun intArgument(name: String, min: Int, max: Int): NotNullArgument<Int> {
        val argument = NotNullIntArgument(name, min, max)

        addArgument(argument)

        return argument
    }

    override fun intArgument(
        name: String,
        optional: Boolean,
        min: Int,
        max: Int
    ): Argument<Int> {
        val argument = IntArgument(name, optional = optional, min = min, max = max)

        addArgument(argument)

        return argument
    }

    override fun entitiesArgument(name: String): NotNullArgument<Set<Entity>> {
        val argument = NotNullEntitiesArgument(name, false)

        addArgument(argument)

        return argument
    }

    override fun entitiesArgument(name: String, optional: Boolean): Argument<Set<Entity>> {
        val argument = EntitiesArgument(name, optional = optional, playerOnly = false)

        addArgument(argument)

        return argument
    }

    override fun option(name: String, configure: CommandBuilder.() -> Unit) {
        val commandBuilder = commandBuilderFactory.create(issuer)
        commandBuilder.configure()
        val argument = OptionArgument(name, optional = false)

        val optionExecutor = commandBuilder.rootExecutor
        if (optionExecutor != null) {
            argument.executor = optionExecutor
        }

        val lastArgument = lastArgument
        if (lastArgument is OptionArgument) {
            val lastArgumentParent = lastArgument.parent
            if (lastArgumentParent == null) {
                rootArguments.add(argument)
            } else {
                lastArgumentParent.addChild(argument)
            }
        } else {
            addArgument(argument)
        }
    }

    override fun execute(executor: () -> Unit) {
        val lastParameter = lastArgument
        if (lastParameter == null) {
            rootExecutor = executor
        } else {
            lastParameter.executor = executor
        }
    }

    private fun executeCommand(issuer: CommandIssuer, arguments: String) {
        rootArguments.forEach { it.parseRecursively(stringReaderFactory.create(arguments), issuer) }
    }
}
