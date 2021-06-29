package org.craftit.api.resources.plugin

import org.craftit.api.resources.Registry

interface PluginRegistry: Registry<Plugin> {
    fun add(plugin: Plugin)
}
