package org.craftit.api.resources.commands.parameters

data class OptionParameter(
    override val name: String,
    override val children: List<Parameter> = emptyList(), 
    override val optional: Boolean,
) : Parameter
