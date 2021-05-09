package org.craftit.test_plugin.commands

import org.craftit.api.resources.commands.QuickCommand

class HelloWorld(override val id: String) : QuickCommand() {
    override fun Command.define() {
        execute {
            issuer.sendMessage("hello world!")
        }
    }
}
