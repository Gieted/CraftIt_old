package org.craftit.api.resources.commands.argument_parsers

class StringReader(private val string: String) {
    private var currentPosition: Int = 0

    fun nextWord(): String {
        val startPosition = currentPosition

        skipWhitespace()

        var word = ""
        while (nextChar() != ' ') {
            word += consumeChar()
        }

        currentPosition = startPosition

        return word
    }

    fun consumeWord(): String {
        skipWhitespace()

        var word = ""
        while (nextChar() != ' ') {
            word += consumeChar()
        }

        return word
    }
    
    fun skipWord() {
        consumeWord()
    }

    fun nextInt(): Int? {
        val startPosition = currentPosition
        
        skipWhitespace()
        val int = try {
            nextWord().toInt()
        } catch (exception: NumberFormatException) {
            null
        }
        
        currentPosition = startPosition
        
        return int
    }

    fun consumeInt(): Int? {
        skipWhitespace()

        return try {
            nextWord().toInt()
        } catch (exception: NumberFormatException) {
            null
        }
    }
    
    fun skipInt() {
        consumeInt()
    }

    fun skip(count: Int) {
        for (i in 1..count) {
            consumeChar()
        }
    }

    fun skipWhitespace() {
        while (nextChar() == ' ') {
            consumeChar()
        }
    }

    fun hasNext(): Boolean = currentPosition < string.length

    fun nextChar(): Char = string[currentPosition]

    fun consumeChar() = string[currentPosition++]
}
