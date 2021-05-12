package org.craftit.api.resources.commands.parameters

import org.craftit.api.resources.commands.CommandDefinition

interface Parameter {
    val name: String

    operator fun plus(variant: CommandDefinition.CommandVariant): CommandDefinition.CommandVariant =
        CommandDefinition.CommandVariant(listOf(this) + variant.parameters)
}
