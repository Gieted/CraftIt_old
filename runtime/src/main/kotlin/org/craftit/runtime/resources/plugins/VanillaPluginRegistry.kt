package org.craftit.runtime.resources.plugins

import org.craftit.api.resources.plugins.Plugin
import org.craftit.api.resources.plugins.PluginRegistry
import org.craftit.runtime.resources.AbstractRegistry
import javax.inject.Inject

class VanillaPluginRegistry @Inject constructor(): PluginRegistry, AbstractRegistry<Plugin>() {
    override fun add(plugin: Plugin) {
        list.add(plugin)
    }
}
