package org.craftit.runtime.resources.plugins

import org.craftit.api.Server
import org.craftit.runtime.server.ServerScope
import java.io.File
import javax.inject.Inject
import javax.inject.Named

@ServerScope
class PluginLoader @Inject constructor(
    private val server: Server,
    @Named("pluginsDirectory") private val pluginsDirectory: File,
    private val filePluginFactory: FilePlugin.Factory
) {

    fun loadPlugins() {
        pluginsDirectory.walk().filter { it.name.endsWith(".craftit") }.forEach {
            val plugin = filePluginFactory.create(it, server)
            server.plugins.add(plugin)
        }
    }
}
