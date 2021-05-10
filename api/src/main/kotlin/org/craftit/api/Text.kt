package org.craftit.api

data class Text(val spans: List<FormattedText>) {
    data class FormattedText(val content: String, val formatting: Formatting = Formatting())

    operator fun plus(other: Text) = Text(this.spans + other.spans)

    data class Formatting(val color: Color = Color.White, val bold: Boolean = false)

    constructor(text: FormattedText) : this(listOf(text))

    constructor(content: String) : this(FormattedText(content))
}
