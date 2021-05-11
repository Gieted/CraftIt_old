package org.craftit.api.resources.commands.parameters

import org.craftit.api.resources.commands.CommandDefinition

interface Parameter {
    val name: String

    operator fun plus(variation: CommandDefinition.CommandVariation): CommandDefinition.CommandVariation =
        CommandDefinition.CommandVariation(listOf(this) + variation.parameters)
}
