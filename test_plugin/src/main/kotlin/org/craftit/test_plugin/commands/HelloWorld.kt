package org.craftit.test_plugin.commands

import org.craftit.api.Color
import org.craftit.api.CraftIt
import org.craftit.api.resources.commands.Command
import org.craftit.api.text_utils.invoke

class HelloWorld(override val id: String, craftIt: CraftIt) : Command by craftIt.command({
    execute {
        issuer.sendMessage(
            "hello "(Color.aqua, underlined = true, bold = true)
                    + "world!"(Color.red, strikethrough = true, italic = true)
        )
    }
})
