package org.craftit.test_plugin.commands

import org.craftit.api.Color
import org.craftit.api.resources.commands.QuickCommand
import org.craftit.api.text_utils.invoke

class HelloWorld(override val id: String) : QuickCommand() {
    override fun Command.define() {
        execute {
            issuer.sendMessage(
                "hello"(Color.aqua, underlined = true, bold = true) + " "
                        + "world!"(Color.red, strikethrough = true, italic = true)
            )
        }
    }
}
