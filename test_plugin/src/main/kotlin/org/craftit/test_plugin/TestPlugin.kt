package org.craftit.test_plugin

import org.craftit.api.CraftIt
import org.craftit.api.QuickPlugin
import org.craftit.test_plugin.commands.Counter
import org.craftit.test_plugin.commands.Heal
import org.craftit.test_plugin.commands.HelloWorld

class TestPlugin(craftIt: CraftIt) : QuickPlugin(craftIt) {
    override fun Commands.register() {
        "heal" { id -> Heal(id) }
        "hello_world" { id -> HelloWorld(id) }
        "counter" { id -> Counter(id)  }
    }
}
