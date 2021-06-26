package org.craftit.runtime.resources.commands

import org.craftit.api.resources.commands.Command
import org.craftit.api.resources.commands.CommandRegistry
import javax.inject.Inject
import javax.inject.Named

class RuntimeCommandRegistry @Inject constructor(@Named("root") override val root: Command) : CommandRegistry {
    private val entries = mutableSetOf<Entry>()

    data class Entry(
        val command: Command
    )

    override fun get(id: String): Command? = entries.find { it.command.id == id }?.command

    override val size: Int
        get() = entries.size

    override fun contains(element: Command): Boolean = entries.map { it.command }.contains(element)


    override fun containsAll(elements: Collection<Command>): Boolean = entries.map { it.command }.containsAll(elements)

    override fun isEmpty(): Boolean = entries.isEmpty()

    override fun iterator(): Iterator<Command> = entries.map { it.command }.listIterator()
}
