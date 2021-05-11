package org.craftit.api

data class Text(val fragments: List<Fragment>) {
    data class Properties(
        val color: Color = Color.white,
        val bold: Boolean = false,
        val italic: Boolean = false,
        val underlined: Boolean = false,
        val strikethrough: Boolean = false,
        val obfuscated: Boolean = false,
        val insertion: String? = null
    )

    data class Fragment(val content: String, val properties: Properties = Properties())

    operator fun plus(other: Text) = Text(this.fragments + other.fragments)
    
    operator fun plus(string: String) = Text(this.fragments + Fragment(string))

    constructor(fragment: Fragment) : this(listOf(fragment))

    constructor(content: String) : this(Fragment(content))
}
