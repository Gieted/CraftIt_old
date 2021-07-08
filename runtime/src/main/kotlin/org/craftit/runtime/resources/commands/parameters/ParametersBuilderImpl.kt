package org.craftit.runtime.resources.commands.parameters

import com.mojang.brigadier.tree.ArgumentCommandNode
import com.mojang.brigadier.tree.LiteralCommandNode
import org.craftit.api.resources.commands.parameters.*
import org.craftit.runtime.resources.commands.ParameterConverter
import javax.inject.Inject

class ParametersBuilderImpl private constructor(
    private val scopeParameter: MutableParameter?,
    private val parameterConverter: ParameterConverter
) : ParametersBuilder {

    @Inject
    constructor(parameterConverter: ParameterConverter) : this(null, parameterConverter)

    private abstract class MutableParameter : Parameter, ParametersBuilder.ParameterRef {
        private val mutableChildren = mutableListOf<Parameter>()
        override val children: List<Parameter> = mutableChildren

        fun addChild(parameter: MutableParameter) {
            mutableChildren.add(parameter)
        }
    }

    private inner class IntParameterImpl(
        override val name: String,
        override val optional: Boolean,
        override val min: Int,
        override val max: Int,
    ) : MutableParameter(), IntParameter {
        override fun <T> toBrigadierCommandNode(): ArgumentCommandNode<T, Int> =
            parameterConverter.convertIntParameter(this)
    }

    private inner class EntityParameterImpl(
        override val name: String,
        override val optional: Boolean,
        override val multiple: Boolean,
        override val playerOnly: Boolean,
    ) : MutableParameter(), EntityParameter {
        override fun <T> toBrigadierCommandNode(): ArgumentCommandNode<T, Any> =
            parameterConverter.convertEntityParameter(this)
    }

    private inner class OptionParameterImpl(
        override val name: String,
        override val optional: Boolean
    ) : MutableParameter(), OptionParameter {
        override fun <T> toBrigadierCommandNode(): LiteralCommandNode<T> =
            parameterConverter.convertOptionParameter(this)
    }

    private val parameters = mutableListOf<MutableParameter>()

    override fun int(
        name: String,
        optional: Boolean,
        min: Int,
        max: Int,
        children: ParametersBuilder.(ParametersBuilder.ParameterRef) -> Unit
    ) {
        val parameter = IntParameterImpl(name, optional, min, max)
        val parameterBuilder = ParametersBuilderImpl(parameter, parameterConverter)
        parameterBuilder.children(parameter)
        parameterBuilder.parameters.forEach { parameter.addChild(it) }
        parameters.add(parameter)
    }

    override fun entity(
        name: String,
        optional: Boolean,
        multiple: Boolean,
        playerOnly: Boolean,
        children: ParametersBuilder.(ParametersBuilder.ParameterRef) -> Unit
    ) {
        val parameter = EntityParameterImpl(name, optional, multiple, playerOnly)
        val parameterBuilder = ParametersBuilderImpl(parameter, parameterConverter)
        parameterBuilder.children(parameter)
        parameterBuilder.parameters.forEach { parameter.addChild(it) }
        parameters.add(parameter)
    }

    override fun option(
        name: String,
        optional: Boolean,
        children: ParametersBuilder.(ParametersBuilder.ParameterRef) -> Unit
    ) {
        val parameter = OptionParameterImpl(name, optional)
        val parameterBuilder = ParametersBuilderImpl(parameter, parameterConverter)
        parameterBuilder.children(parameter)
        parameterBuilder.parameters.forEach { parameter.addChild(it) }
        parameters.add(parameter)
    }

    private operator fun List<Parameter>.invoke(refCache: MutableMap<Parameter, ParametersBuilder.ParameterRef>) {
        forEach { 
            it(refCache)
        }
    }


    override fun List<Parameter>.invoke() {
        val refCache = mutableMapOf<Parameter, ParametersBuilder.ParameterRef>()

        this(refCache)
    }

    private operator fun Parameter.invoke(refCache: MutableMap<Parameter, ParametersBuilder.ParameterRef>) {
        refCache[this]?.invoke() ?: when (val parameter = this) {
            is IntParameter -> int(
                parameter.name,
                parameter.optional,
                parameter.min,
                parameter.max
            ) {
                refCache[this@invoke] = it
                parameter.children(refCache)
            }
            is EntityParameter -> entity(
                parameter.name,
                parameter.optional,
                parameter.multiple,
                parameter.playerOnly
            ) {
                refCache[this@invoke] = it
                parameter.children(refCache)
            }
            is OptionParameter -> option(parameter.name, parameter.optional) {
                refCache[this@invoke] = it
                parameter.children(refCache)
            }
            else -> throw AssertionError()
        }
    }

    override operator fun Parameter.invoke() {
        val refCache = mutableMapOf<Parameter, ParametersBuilder.ParameterRef>()

        this(refCache)
    }

    override fun ParametersBuilder.ParameterRef.invoke() {
        scopeParameter!!.addChild(this as MutableParameter)
    }

    operator fun invoke(configure: ParametersBuilder.() -> Unit): List<Parameter> {
        this.configure()

        return this.build()
    }

    fun build(): List<Parameter> = parameters
}
