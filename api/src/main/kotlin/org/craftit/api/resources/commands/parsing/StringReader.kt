package org.craftit.api.resources.commands.parsing

interface StringReader {
    
    interface Factory {
        fun create(string: String): StringReader
    }

    val nextWord: String?

    fun consumeWord(): String

    val nextInt: Int?

    fun consumeInt(): Int

    val nextChar: Char?

    fun consumeChar(): Char
    
    fun consumeWhitespace()
    
    fun clone(): StringReader
}
