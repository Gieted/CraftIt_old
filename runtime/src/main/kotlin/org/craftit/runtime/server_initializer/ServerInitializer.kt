package org.craftit.runtime.server_initializer

import org.craftit.runtime.Bridge
import org.craftit.runtime.bytecode_modifiers.ServerBytecodeModifier
import org.craftit.runtime.plugin.PluginLoader

abstract class ServerInitializer(
    private val bridge: Bridge,
    private val bytecodeModifier: ServerBytecodeModifier,
    private val pluginLoader: PluginLoader
) {
    open fun startServer() {
        bytecodeModifier.modify()
        bridge.setup()
        pluginLoader.loadPlugins()
    }
}
