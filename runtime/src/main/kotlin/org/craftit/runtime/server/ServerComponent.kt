package org.craftit.runtime.server

import dagger.BindsInstance
import dagger.Subcomponent
import org.craftit.api.Server
import org.craftit.api.resources.commands.CommandRegistry
import org.craftit.api.resources.entities.EntityRegistry
import org.craftit.api.resources.items.ItemRegistry
import org.craftit.api.resources.plugins.PluginRegistry
import org.craftit.runtime.resources.plugins.PluginLoader
import org.craftit.runtime.server.initializers.NativeServerInitializer

@ServerScope
@Subcomponent(modules = [ServerModule::class])
interface ServerComponent {

    fun pluginLoader(): PluginLoader

    fun entityRegistry(): EntityRegistry

    fun nativeServerInitializer(): NativeServerInitializer

    fun commandRegistry(): CommandRegistry

    fun pluginRegistry(): PluginRegistry

    fun itemRegistry(): ItemRegistry

    @Subcomponent.Factory
    interface Factory {
        fun create(@BindsInstance server: Server): ServerComponent
    }
}
