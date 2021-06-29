package org.craftit.runtime.resources.plugin

import org.craftit.api.resources.plugin.Plugin
import org.craftit.api.resources.plugin.PluginRegistry
import org.craftit.runtime.resources.ListRegistry
import javax.inject.Inject

class VanillaPluginRegistry @Inject constructor(): PluginRegistry, ListRegistry<Plugin>() {
    override fun add(plugin: Plugin) {
        list.add(plugin)
    }
}
