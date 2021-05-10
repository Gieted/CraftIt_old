package org.craftit.api.text_utils

import org.craftit.api.Color
import org.craftit.api.Text

infix fun String.color(color: Color) = Text(Text.FormattedText(this, Text.Formatting(color)))

operator fun String.invoke(color: Color = Color.White, bold: Boolean = false) =
    Text(Text.FormattedText(this, Text.Formatting(color, bold)))

fun String.toText() = Text(this)
