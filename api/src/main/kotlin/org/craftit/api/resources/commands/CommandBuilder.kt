package org.craftit.api.resources.commands

import org.craftit.api.resources.commands.arguments.Argument
import org.craftit.api.resources.commands.arguments.NotNullArgument
import org.craftit.api.resources.entities.Entity

interface CommandBuilder {
    val issuer: CommandIssuer

    fun intArgument(
        name: String,
        min: Int = Int.MIN_VALUE,
        max: Int = Int.MAX_VALUE,
    ): NotNullArgument<Int>

    fun intArgument(
        name: String,
        optional: Boolean,
        min: Int = Int.MIN_VALUE,
        max: Int = Int.MAX_VALUE,
    ): Argument<Int>

    fun entitiesArgument(name: String): NotNullArgument<Set<Entity>>

    fun entitiesArgument(name: String, optional: Boolean): Argument<Set<Entity>>

    fun option(name: String, configure: CommandBuilder.() -> Unit)

    fun execute(executor: () -> Unit)
}
