package org.craftit.api.resources.commands

import org.craftit.api.resources.commands.parameters.Parameter

data class CommandDefinition(val variations: List<CommandVariation>) {
    data class CommandVariation(val parameters: List<Parameter> = emptyList()) {
        operator fun plus(parameter: Parameter) = CommandVariation(parameters + parameter)

        operator fun plus(other: CommandVariation) =
            CommandVariation(parameters + other.parameters)
    }
}
