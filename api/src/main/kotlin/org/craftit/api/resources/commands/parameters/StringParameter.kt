package org.craftit.api.resources.commands.parameters

interface StringParameter : Parameter {
    enum class Type {
        Word, Quote, Phrase
    }

    val type: Type
}
