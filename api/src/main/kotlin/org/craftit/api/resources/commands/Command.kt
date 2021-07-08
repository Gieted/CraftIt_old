package org.craftit.api.resources.commands

import org.craftit.api.Text
import org.craftit.api.resources.Resource

interface Command : Resource {

    fun interface Factory {
        fun create(id: String): Command
    }
    
    fun getDefinition(issuer: CommandIssuer): CommandDefinition

    fun execute(issuer: CommandIssuer, arguments: String)

    data class Suggestions(val replaceRange: IntRange, val list: List<String>, val tooltip: Text?)

    fun getSuggestions(issuer: CommandIssuer, currentArguments: String): Suggestions
    
    val state: Any
      get() = Any()
}
