package org.craftit.api.resources.commands

interface CommandParser {
    data class Result(val id: String, val arguments: String)

    fun parse(commandString: String): Result
}
