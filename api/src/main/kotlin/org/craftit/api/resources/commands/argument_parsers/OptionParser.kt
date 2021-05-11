package org.craftit.api.resources.commands.argument_parsers

import org.craftit.api.resources.commands.CommandIssuer
import java.lang.Exception

class OptionParser(private val option: String) : ArgumentParser<Unit> {
    override fun parse(iterator: CharIterator, commandIssuer: CommandIssuer) {
        var argument = ""
        var currentChar: Char = iterator.nextChar()
        while (currentChar != ' ') {
            argument += currentChar
            if (iterator.hasNext()) {
                currentChar = iterator.nextChar()
            } else {
                break
            }
        }
        
        if (argument.trim() != option) {
            throw Exception()
        }
    }
}
