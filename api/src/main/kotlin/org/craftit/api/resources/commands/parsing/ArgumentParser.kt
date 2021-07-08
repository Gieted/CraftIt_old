package org.craftit.api.resources.commands.parsing

import org.craftit.api.resources.commands.CommandIssuer

fun interface ArgumentParser<T : Any> {
    fun parse(reader: StringReader, issuer: CommandIssuer): T
}
