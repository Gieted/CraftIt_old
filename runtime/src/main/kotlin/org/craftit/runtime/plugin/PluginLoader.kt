package org.craftit.runtime.plugin

import java.io.File
import javax.inject.Inject
import javax.inject.Named

class PluginLoader @Inject constructor(@Named("pluginDirectory") private val pluginDirectory: File, private val filePluginFactory: FilePlugin.Factory) {
    fun loadPlugins() {
        pluginDirectory.walk().filter { it.name.endsWith(".craftit") }.forEach { filePluginFactory.create(it).enable() }
    }
}
