package org.craftit.runtime.resources.commands.parsing.argument_parsers

import org.craftit.api.resources.commands.parsing.ArgumentParser
import org.craftit.api.resources.commands.CommandIssuer
import org.craftit.api.resources.commands.parsing.StringReader
import javax.inject.Inject

class IntParser @Inject constructor() : ArgumentParser<Int> {
    override fun parse(reader: StringReader, issuer: CommandIssuer): Int = reader.consumeInt()

}
