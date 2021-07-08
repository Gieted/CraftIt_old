package org.craftit.runtime.resources.commands.parameters.argument_parsers

import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import org.craftit.api.resources.commands.parsing.ArgumentParser
import org.craftit.api.resources.commands.CommandIssuer
import org.craftit.api.resources.commands.parsing.ParsingException
import org.craftit.api.resources.commands.parsing.StringReader

class OptionParser @AssistedInject constructor(@Assisted private val option: String) : ArgumentParser<Unit> {

    @AssistedFactory
    interface Factory {
        fun create(option: String): OptionParser
    }

    override fun parse(reader: StringReader, issuer: CommandIssuer) {
        if (reader.consumeWord() != option) {
            throw ParsingException()
        }
    }
}
