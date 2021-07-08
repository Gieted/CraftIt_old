package org.craftit.runtime.resources.commands

import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import org.craftit.api.resources.commands.CommandBuilder
import org.craftit.api.resources.commands.CommandDefinition
import org.craftit.api.resources.commands.CommandIssuer
import org.craftit.api.resources.commands.parameters.ParametersBuilder
import org.craftit.api.resources.entities.Entity
import org.craftit.runtime.resources.commands.parameters.ParametersBuilderImpl
import javax.inject.Provider
import kotlin.reflect.KProperty

@Suppress("UNCHECKED_CAST")
class CommandBuilderImpl @AssistedInject constructor(
    @Assisted override val issuer: CommandIssuer,
    private val commandBuilderFactory: Factory,
    private val parameterBuilderProvider: Provider<ParametersBuilderImpl>
) : CommandBuilder {

    @AssistedFactory
    interface Factory {
        fun create(issuer: CommandIssuer): CommandBuilderImpl
    }

    private abstract inner class Argument<T> : CommandBuilder.Argument<T> {
        abstract val name: String
        abstract val optional: Boolean
        val children = mutableListOf<Argument<*>>()
        var parent: Argument<*>? = null
            private set

        override operator fun getValue(thisRef: Any?, prop: KProperty<*>): T {
            return TODO()
        }

        fun addChild(argument: Argument<*>) {
            children.add(argument)
            argument.parent = this
        }

        abstract fun ParametersBuilder.parameter()
    }

    private inner class IntArgument<T : Int?>(
        override val name: String,
        override val optional: Boolean,
        val min: Int,
        val max: Int
    ) : Argument<T>() {
        override fun ParametersBuilder.parameter() {
            int(name, optional, min, max) { 
                children.forEach { it.apply { parameter() } }
            }
        }
    }

    private inner class EntitiesArgument<T : Set<Entity>?>(
        override val name: String,
        override val optional: Boolean,
        val playerOnly: Boolean,
    ) : Argument<T>() {
        override fun ParametersBuilder.parameter() {
            entity(name, optional, true, playerOnly) {
                children.forEach { it.apply { parameter() } }
            }
        }
    }

    private inner class OptionArgument(
        override val name: String,
        override val optional: Boolean,
    ) : Argument<String>() {
        override fun ParametersBuilder.parameter() {
            option(name, optional) {
                children.forEach { it.apply { parameter() } }
            }
        }
    }

    private var lastArgument: Argument<*>? = null
    private val rootArguments = mutableListOf<Argument<*>>()

    private val executors = mutableMapOf<Argument<*>, () -> Unit>()
    private var rootExecutor: (() -> Unit)? = null

    val definition: CommandDefinition
        get() = CommandDefinition((parameterBuilderProvider.get()){
            rootArguments.forEach { it.apply { parameter() } }
        })

    private fun addArgument(parameter: Argument<*>) {
        val lastArgument = lastArgument
        if (lastArgument == null) {
            rootArguments.add(parameter)
            this.lastArgument = parameter
        } else {
            lastArgument.addChild(parameter)
            this.lastArgument = parameter
        }
    }

    override fun intArgument(name: String, min: Int, max: Int): CommandBuilder.Argument<Int> =
        intArgument(name, false, min, max) as CommandBuilder.Argument<Int>

    override fun intArgument(name: String, optional: Boolean, min: Int, max: Int): CommandBuilder.Argument<Int?> {
        val argument = IntArgument<Int?>(name, optional = optional, min = min, max = max)

        addArgument(argument)

        return argument
    }

    override fun entitiesArgument(name: String): CommandBuilder.Argument<Set<Entity>> =
        entitiesArgument(name, false) as CommandBuilder.Argument<Set<Entity>>

    override fun entitiesArgument(name: String, optional: Boolean): CommandBuilder.Argument<Set<Entity>?> {
        val argument = EntitiesArgument<Set<Entity>?>(name, optional = optional, playerOnly = false)

        addArgument(argument)

        return argument
    }

    override fun option(name: String, configure: CommandBuilder.() -> Unit) {
        val commandBuilder = commandBuilderFactory.create(issuer)
        commandBuilder.configure()
        val parameter = OptionArgument(name, optional = false)
        executors.putAll(commandBuilder.executors)

        val optionExecutor = commandBuilder.rootExecutor
        if (optionExecutor != null) {
            executors[parameter] = optionExecutor
        }

        val lastArgument = lastArgument
        if (lastArgument is OptionArgument) {
            val lastArgumentParent = lastArgument.parent
            if (lastArgumentParent == null) {
                rootArguments.add(parameter)
            } else {
                lastArgumentParent.addChild(parameter)
            }
        } else {
            addArgument(parameter)
        }
    }

    override fun execute(executor: () -> Unit) {
        val lastParameter = lastArgument
        if (lastParameter == null) {
            rootExecutor = executor
        } else {
            executors[lastParameter] = executor
        }
    }
}
