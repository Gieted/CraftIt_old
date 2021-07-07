package org.craftit.api.resources.commands.parameters

data class EntityParameter(
    override val name: String,
    override val children: List<Parameter> = emptyList(),
    override val optional: Boolean,
    val multiple: Boolean,
    val playerOnly: Boolean,
) : Parameter
