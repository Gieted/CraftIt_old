package org.craftit.api.resources.commands

import org.craftit.api.resources.entities.Entity
import kotlin.reflect.KProperty

interface CommandBuilder {
    val issuer: CommandIssuer

    interface Argument<T> {
        operator fun getValue(thisRef: Any?, prop: KProperty<*>): T
    }

    fun intArgument(
        name: String,
        min: Int = Int.MIN_VALUE,
        max: Int = Int.MAX_VALUE,
    ): Argument<Int>

    fun intArgument(
        name: String,
        optional: Boolean,
        min: Int = Int.MIN_VALUE,
        max: Int = Int.MAX_VALUE,
    ): Argument<Int?>

    fun entitiesArgument(name: String): Argument<Set<Entity>>

    fun entitiesArgument(name: String, optional: Boolean): Argument<Set<Entity>?>

    fun option(name: String, configure: CommandBuilder.() -> Unit)

    fun execute(executor: () -> Unit)
}
