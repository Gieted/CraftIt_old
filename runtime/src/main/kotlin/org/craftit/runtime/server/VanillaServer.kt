package org.craftit.runtime.server

import org.craftit.api.Server
import org.craftit.api.resources.commands.CommandRegistry
import org.craftit.api.resources.entities.EntityRegistry
import org.craftit.api.resources.plugins.PluginRegistry
import org.craftit.runtime.resources.plugins.PluginLoader
import org.craftit.runtime.server.initializers.NativeServerInitializer
import javax.inject.Inject

class VanillaServer @Inject constructor(
    serverComponentFactory: ServerComponent.Factory
) : Server {

    override val entities: EntityRegistry
    override val commands: CommandRegistry
    override val plugins: PluginRegistry

    private val pluginLoader: PluginLoader

    private val nativeServerInitializer: NativeServerInitializer

    init {
        val component = serverComponentFactory.create(this)
        pluginLoader = component.pluginLoader()
        entities = component.entityRegistry()
        nativeServerInitializer = component.nativeServerInitializer()
        commands = component.commandRegistry()
        plugins = component.pluginRegistry()
    }

    override fun start() {
        nativeServerInitializer.startServer()
        pluginLoader.loadPlugins()
        plugins.forEach { it.enable() }
    }
}
