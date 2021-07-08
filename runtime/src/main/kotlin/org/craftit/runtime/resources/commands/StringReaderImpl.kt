package org.craftit.runtime.resources.commands

import org.craftit.api.resources.commands.parsing.ParsingException
import org.craftit.api.resources.commands.parsing.StringReader

class StringReaderImpl(private val string: String) : StringReader {
    private var currentPosition: Int = 0

    override val nextWord: String?
        get() {
            val startPosition = currentPosition

            return try {
                consumeWord()
            } catch (exception: ParsingException) {
                null
            }.also { currentPosition = startPosition }
        }

    override fun consumeWord(): String {
        var word = ""

        consumeWhitespace()
        while (nextChar != ' ') {
            word += consumeChar()
        }

        return word
    }

    override val nextInt: Int?
        get() {
            val startPosition = currentPosition

            return try {
                consumeInt()
            } catch (exception: ParsingException) {
                null
            }.also { currentPosition = startPosition }
        }

    override fun consumeInt(): Int {
        val word = consumeWord()

        return word.toInt()
    }

    override val nextChar: Char?
        get() = if (currentPosition < string.length) string[currentPosition] else null

    override fun consumeChar(): Char {
        return string[currentPosition].also { currentPosition++ }
    }

    override fun consumeWhitespace() {
        while (nextChar?.isWhitespace() == true) {
            consumeChar()
        }
    }
}
