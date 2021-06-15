package org.craftit.test_plugin.commands

import org.craftit.api.resources.commands.QuickCommand

class Counter(override val id: String) : QuickCommand() {
    private var count: Int = 0
    
    override fun Command.define() {
        option("add") {
            execute { 
                count++
                issuer.sendSystemMessage(count.toString())
            }
        }
        
        option("subtract") {
            execute { 
                count--
                issuer.sendSystemMessage(count.toString())
            }
        }
    }
}
