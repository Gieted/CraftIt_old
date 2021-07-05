package org.craftit.api.resources.commands

interface CommandRegistry: Collection<Command> {
    val root: Command

    operator fun get(id: String): Command?
    
    fun add(command: Command)
}
