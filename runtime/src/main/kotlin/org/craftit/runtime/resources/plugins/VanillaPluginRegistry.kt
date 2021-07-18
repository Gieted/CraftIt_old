package org.craftit.runtime.resources.plugins

import org.craftit.api.resources.plugins.Plugin
import org.craftit.api.resources.plugins.PluginRegistry
import org.craftit.runtime.resources.ListRegistry
import javax.inject.Inject

class VanillaPluginRegistry @Inject constructor(): PluginRegistry, ListRegistry<Plugin>() {
    override fun add(plugin: Plugin) {
        list.add(plugin)
    }
}
