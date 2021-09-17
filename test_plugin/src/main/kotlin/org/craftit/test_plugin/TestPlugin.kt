package org.craftit.test_plugin

import org.craftit.api.CraftIt
import org.craftit.api.resources.plugins.Plugin

class TestPlugin(craftIt: CraftIt) : Plugin by craftIt.plugin({
    /*commands {
        "hello_world" { HelloWorld(it, craftIt) }
        "heal" { Heal(it, craftIt) }
        "counter" { Counter(it, craftIt) }
    }*/
})
