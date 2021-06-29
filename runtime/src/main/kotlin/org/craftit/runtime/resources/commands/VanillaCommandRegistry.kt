package org.craftit.runtime.resources.commands

import org.craftit.api.resources.commands.Command
import org.craftit.api.resources.commands.CommandRegistry
import javax.inject.Inject
import javax.inject.Named

class VanillaCommandRegistry @Inject constructor(
    @Named("root") override val root: Command,
) : CommandRegistry {
    
    private val commands = mutableListOf<Command>()

    override fun get(id: String): Command? = commands.find { it.id == id }

    override fun add(command: Command) {
        commands.add(command)
    }

    override val size: Int
        get() = commands.size

    override fun contains(element: Command): Boolean = commands.contains(element)

    override fun containsAll(elements: Collection<Command>): Boolean = commands.containsAll(elements)

    override fun isEmpty(): Boolean = commands.isEmpty()

    override fun iterator(): Iterator<Command> = commands.iterator()
}
