package org.craftit.api.resources.commands

import org.craftit.api.resources.commands.parameters.Parameter

data class CommandDefinition(val variants: List<CommandVariant>) {
    data class CommandVariant(val parameters: List<Parameter> = emptyList()) {
        operator fun plus(parameter: Parameter) = CommandVariant(parameters + parameter)

        operator fun plus(other: CommandVariant) =
            CommandVariant(parameters + other.parameters)
    }
}
