package org.craftit.runtime.resources.commands.parameters

import com.mojang.brigadier.tree.ArgumentCommandNode
import com.mojang.brigadier.tree.LiteralCommandNode
import org.craftit.api.resources.commands.parameters.*
import org.craftit.runtime.resources.commands.ParameterConverter
import javax.inject.Inject

class ParametersBuilderImpl private constructor(
    private val scopeParameter: MutableParameter?,
    rootParameters: List<MutableParameter>? = null,
    private val parameterConverter: ParameterConverter,
) : ParametersBuilder {

    @Inject
    constructor(parameterConverter: ParameterConverter) : this(null, null, parameterConverter)

    private abstract class MutableParameter : Parameter, ParametersBuilder.ParameterRef {
        private val mutableChildren = mutableListOf<MutableParameter>()
        val refs = mutableListOf<List<MutableParameter>>()
        override val children: List<MutableParameter> = mutableChildren + refs.flatten()

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
    private val rootParameters = rootParameters ?: parameters

    override fun int(
        name: String,
        optional: Boolean,
        min: Int,
        max: Int,
        children: ParametersBuilder.(ParametersBuilder.ParameterRef) -> Unit
    ): ParametersBuilder.ParameterRef {
        val parameter = IntParameterImpl(name, optional, min, max)
        val parameterBuilder = ParametersBuilderImpl(parameter, rootParameters, parameterConverter)
        parameterBuilder.children(parameter)
        parameterBuilder.parameters.forEach { parameter.addChild(it) }
        parameters.add(parameter)

        return parameter
    }

    override fun entity(
        name: String,
        optional: Boolean,
        multiple: Boolean,
        playerOnly: Boolean,
        children: ParametersBuilder.(ParametersBuilder.ParameterRef) -> Unit
    ): ParametersBuilder.ParameterRef {
        val parameter = EntityParameterImpl(name, optional, multiple, playerOnly)
        val parameterBuilder = ParametersBuilderImpl(parameter, rootParameters, parameterConverter)
        parameterBuilder.children(parameter)
        parameterBuilder.parameters.forEach { parameter.addChild(it) }
        parameters.add(parameter)

        return parameter
    }

    override fun option(
        name: String,
        optional: Boolean,
        children: ParametersBuilder.(ParametersBuilder.ParameterRef) -> Unit
    ): ParametersBuilder.ParameterRef {
        val parameter = OptionParameterImpl(name, optional)
        val parameterBuilder = ParametersBuilderImpl(parameter, rootParameters, parameterConverter)
        parameterBuilder.children(parameter)
        parameterBuilder.parameters.forEach { parameter.addChild(it) }
        parameters.add(parameter)

        return parameter
    }

    private fun ParametersBuilder.parameters(
        refCache: MutableMap<Parameter, ParametersBuilder.ParameterRef>,
        parameters: List<Parameter>
    ) {
        parameters.forEach {
            parameter(refCache, it)
        }
    }


    override fun List<Parameter>.invoke() {
        val refCache = mutableMapOf<Parameter, ParametersBuilder.ParameterRef>()

        parameters(refCache, this)
    }

    private fun ParametersBuilder.parameter(
        refCache: MutableMap<Parameter, ParametersBuilder.ParameterRef>,
        parameter: Parameter
    ) {
        refCache[parameter]?.invoke() ?: when (parameter) {
            is IntParameter -> int(
                parameter.name,
                parameter.optional,
                parameter.min,
                parameter.max
            ) {
                refCache[parameter] = it
                parameters(refCache, parameter.children)
            }
            is EntityParameter -> entity(
                parameter.name,
                parameter.optional,
                parameter.multiple,
                parameter.playerOnly
            ) {
                refCache[parameter] = it
                parameters(refCache, parameter.children)
            }
            is OptionParameter -> option(parameter.name, parameter.optional) {
                refCache[parameter] = it
                parameters(refCache, parameter.children)
            }
            else -> throw AssertionError()
        }
    }

    override operator fun Parameter.invoke() {
        val refCache = mutableMapOf<Parameter, ParametersBuilder.ParameterRef>()

        parameter(refCache, this)
    }

    override fun ParametersBuilder.ParameterRef.invoke() {
        scopeParameter!!.addChild(this as MutableParameter)
    }

    override fun ParametersBuilder.ParameterRef.children() {
        scopeParameter!!.refs.add((this as MutableParameter).children)
    }


    override fun root() {
        scopeParameter!!.refs.add(rootParameters)
    }

    operator fun invoke(configure: ParametersBuilder.() -> Unit): List<Parameter> {
        this.configure()

        return this.build()
    }

    fun build(): List<Parameter> = parameters
}
