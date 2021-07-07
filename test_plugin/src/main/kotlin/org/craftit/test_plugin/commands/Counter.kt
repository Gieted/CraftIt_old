package org.craftit.test_plugin.commands

import org.craftit.api.CraftIt
import org.craftit.api.resources.commands.Command

class Counter(
    override val id: String,
    craftIt: CraftIt,
    override val state: State = State()
) : Command by craftIt.command({
    
    option("add") {
        execute {
            state.count++
            issuer.sendMessage(state.count.toString())
        }
    }

    option("subtract") {
        execute {
            state.count--
            issuer.sendMessage(state.count.toString())
        }
    }
}) {
    data class State(var count: Int = 0)
}
