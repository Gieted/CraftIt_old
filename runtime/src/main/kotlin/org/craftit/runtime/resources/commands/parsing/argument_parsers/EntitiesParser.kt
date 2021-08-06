package org.craftit.runtime.resources.commands.parsing.argument_parsers

import org.craftit.api.resources.commands.parsing.ArgumentParser
import org.craftit.api.resources.commands.CommandIssuer
import org.craftit.api.resources.commands.parsing.StringReader
import org.craftit.api.resources.entities.Entity
import javax.inject.Inject

class EntitiesParser @Inject constructor() : ArgumentParser<List<Entity>> {
    override fun parse(reader: StringReader, issuer: CommandIssuer): List<Entity> {
        reader.consumeWord()
        
        return listOf((issuer as Entity))
    }
}
