package org.craftit.api

import org.craftit.api.resources.items.ItemRegistry
import org.craftit.api.resources.commands.CommandRegistry
import org.craftit.api.resources.entities.EntityRegistry
import org.craftit.api.resources.plugins.PluginRegistry

interface Server {
    val commands: CommandRegistry
    val entities: EntityRegistry
    val plugins: PluginRegistry
    val items: ItemRegistry
    
    fun start()
}
