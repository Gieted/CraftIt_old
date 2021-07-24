package org.craftit.runtime.resources.plugins.api.builders

import io.kotest.core.spec.style.FunSpec
import io.mockk.mockk
import io.mockk.verify
import org.craftit.api.CraftIt

class PluginBuilderImplTest : FunSpec({
    val craftIt = mockk<CraftIt>(relaxed = true)
    val pluginBuilder = PluginBuilderImpl(craftIt)
    
    
    test("builds a plugin") {
        pluginBuilder.build()
    }
    
    test("registers provided commands") {
        val plugin = pluginBuilder.apply { 
            commands { 
                "one" { mockk() }
                "two" { mockk() }
            }
        }.build()
        
        plugin.enable()
        
        val commands = craftIt.commands
        verify(exactly = 1) { commands.register("one", any()) }
        verify(exactly = 1) { commands.register("two", any()) }
    }
})
