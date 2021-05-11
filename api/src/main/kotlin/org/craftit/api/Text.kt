package org.craftit.api

data class Text(val spans: List<FormattedText>) {
    data class Properties(
        val color: Color = Color.white,
        val bold: Boolean = false,
        val italic: Boolean = false,
        val underlined: Boolean = false,
        val strikethrough: Boolean = false,
        val obfuscated: Boolean = false,
        val insertion: String? = null
    )

    data class FormattedText(val content: String, val properties: Properties = Properties())

    operator fun plus(other: Text) = Text(this.spans + other.spans)
    
    operator fun plus(string: String) = Text(this.spans + FormattedText(string))

    constructor(text: FormattedText) : this(listOf(text))

    constructor(content: String) : this(FormattedText(content))
}
