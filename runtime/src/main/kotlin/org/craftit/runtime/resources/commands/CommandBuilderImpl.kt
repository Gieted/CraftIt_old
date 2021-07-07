package org.craftit.runtime.resources.commands

import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import org.craftit.api.resources.commands.CommandBuilder
import org.craftit.api.resources.commands.CommandDefinition
import org.craftit.api.resources.commands.CommandIssuer
import org.craftit.api.resources.commands.parameters.EntityParameter
import org.craftit.api.resources.commands.parameters.NumericParameter
import org.craftit.api.resources.commands.parameters.OptionParameter
import org.craftit.api.resources.commands.parameters.Parameter
import org.craftit.api.resources.entities.Entity
import kotlin.reflect.KClass
import kotlin.reflect.KProperty

@Suppress("UNCHECKED_CAST")
class CommandBuilderImpl @AssistedInject constructor(
    @Assisted override val issuer: CommandIssuer,
    private val commandBuilderFactory: Factory
) : CommandBuilder {

    @AssistedFactory
    interface Factory {
        fun create(issuer: CommandIssuer): CommandBuilderImpl
    }

    private class ArgumentImpl<T> : CommandBuilder.Argument<T> {
        override operator fun getValue(thisRef: Any?, prop: KProperty<*>): T {
            return TODO()
        }
    }

    private data class MutableCommandDefinition(val rootParameters: MutableList<MutableParameter> = mutableListOf()) {
        fun toCommandDefinition() = CommandDefinition(rootParameters.map { it.toParameter() })
    }

    private val parameterCache = mutableMapOf<MutableParameter, Parameter>()

    private interface MutableParameter {
        val name: String
        val children: MutableList<MutableParameter>
        var parent: MutableParameter?
        val optional: Boolean

        fun addChild(child: MutableParameter) {
            children.add(child)
            child.parent = this
        }

        fun toParameter(): Parameter
    }


    private inner class MutableEntityParameter(
        override val name: String,
        override val children: MutableList<MutableParameter> = mutableListOf(),
        override var parent: MutableParameter? = null,
        override val optional: Boolean,
        val multiple: Boolean,
        val playerOnly: Boolean,
    ) : MutableParameter {
        override fun toParameter() = parameterCache.getOrPut(this) {
            EntityParameter(
                name,
                children.map { it.toParameter() },
                optional,
                multiple,
                playerOnly
            )
        }
    }

    private inner class MutableNumericParameter<T : Comparable<T>>(
        override val name: String,
        override val children: MutableList<MutableParameter> = mutableListOf(),
        override var parent: MutableParameter? = null,
        override val optional: Boolean,
        val min: T?,
        val max: T?,
        val type: KClass<T>,
    ) : MutableParameter {
        override fun toParameter() = parameterCache.getOrPut(this) {
            NumericParameter(name, children.map { it.toParameter() }, optional, min, max, type)
        }
    }

    private inner class MutableOptionParameter(
        override val name: String,
        override val children: MutableList<MutableParameter> = mutableListOf(),
        override var parent: MutableParameter? = null,
        override val optional: Boolean,
    ) : MutableParameter {
        override fun toParameter() = parameterCache.getOrPut(this) {
            OptionParameter(name, children.map { it.toParameter() }, optional)
        }
    }

    private val mutableDefinition = MutableCommandDefinition()
    private var lastParameter: MutableParameter? = null

    val definition: CommandDefinition
        get() = mutableDefinition.toCommandDefinition()

    private fun addParameter(parameter: MutableParameter) {
        val lastParameter = lastParameter
        if (lastParameter == null) {
            mutableDefinition.rootParameters.add(parameter)
            this.lastParameter = parameter
        } else {
            lastParameter.addChild(parameter)
            this.lastParameter = parameter
        }
    }

    override fun intArgument(name: String, min: Int, max: Int): CommandBuilder.Argument<Int> =
        intArgument(name, false, min, max) as CommandBuilder.Argument<Int>

    override fun intArgument(name: String, optional: Boolean, min: Int, max: Int): CommandBuilder.Argument<Int?> {
        val parameter = MutableNumericParameter(name, optional = optional, min = min, max = max, type = Int::class)

        addParameter(parameter)

        return ArgumentImpl()
    }

    override fun entitiesArgument(name: String): CommandBuilder.Argument<Set<Entity>> =
        entitiesArgument(name, false) as CommandBuilder.Argument<Set<Entity>>

    override fun entitiesArgument(name: String, optional: Boolean): CommandBuilder.Argument<Set<Entity>?> {
        val parameter = MutableEntityParameter(name, optional = optional, multiple = true, playerOnly = false)

        addParameter(parameter)

        return ArgumentImpl()
    }

    override fun option(name: String, configure: CommandBuilder.() -> Unit) {
        val commandBuilder = commandBuilderFactory.create(issuer)
        commandBuilder.configure()
        val parameter = MutableOptionParameter(name, commandBuilder.mutableDefinition.rootParameters, optional = false)

        val lastParameter = lastParameter
        if (lastParameter is MutableOptionParameter) {
            val lastParameterParent = lastParameter.parent
            if (lastParameterParent == null) {
                mutableDefinition.rootParameters.add(parameter)
            } else {
                lastParameterParent.addChild(parameter)
            }
        } else {
            addParameter(parameter)
        }
    }

    override fun execute(executor: () -> Unit) {
    }
}
