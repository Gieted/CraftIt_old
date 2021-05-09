package org.craftit.api.resources.commands.argument_parsers

import org.craftit.api.resources.commands.CommandIssuer

class IntParser : ArgumentParser<Int> {
    override fun parse(iterator: CharIterator, commandIssuer: CommandIssuer): Int {
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

        return argument.trim().toInt()
    }
}
