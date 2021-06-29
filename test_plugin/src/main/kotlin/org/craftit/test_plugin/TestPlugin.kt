package org.craftit.test_plugin

import org.craftit.api.CraftIt
import org.craftit.api.resources.plugin.Plugin
import org.craftit.test_plugin.commands.Counter
import org.craftit.test_plugin.commands.Heal
import org.craftit.test_plugin.commands.HelloWorld

class TestPlugin(craftIt: CraftIt) : Plugin by craftIt.plugin(
    commands = {
        "hello_world" { HelloWorld(it) }
        "heal" { Heal(it) }
        "counter" { Counter(it) }
    }
)
