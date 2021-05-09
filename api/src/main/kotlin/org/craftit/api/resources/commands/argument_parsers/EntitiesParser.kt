package org.craftit.api.resources.commands.argument_parsers

import org.craftit.api.resources.commands.CommandIssuer
import org.craftit.api.resources.entities.Entity

class EntitiesParser : ArgumentParser<List<Entity>> {
    override fun parse(iterator: CharIterator, commandIssuer: CommandIssuer): List<Entity> {
        while (true) {
            if (iterator.nextChar() == ' ') {
                break
            }
        }

        return listOf(commandIssuer as Entity)
    }
}
