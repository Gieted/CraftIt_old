package org.craftit.api.resources.commands.parameters

data class EntityParameter(
    override val name: String,
    val multiple: Boolean,
    val playerOnly: Boolean,
) : Parameter
