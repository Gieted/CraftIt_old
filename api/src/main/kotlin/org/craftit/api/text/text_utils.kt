package org.craftit.api.text

import org.craftit.api.Color
import org.craftit.api.text.Text

operator fun String.invoke(
    color: Color = Color.white,
    bold: Boolean = false,
    italic: Boolean = false,
    underlined: Boolean = false,
    strikethrough: Boolean = false,
    obfuscated: Boolean = false,
    insertion: String? = null
) = Text(
    Text.Fragment(
        this, Text.Properties(
            color,
            bold,
            italic,
            underlined,
            strikethrough,
            obfuscated,
            insertion
        )
    )
)

fun String.toText() = Text(this)
