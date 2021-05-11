package org.craftit.runtime.server_initializer

import org.craftit.runtime.Bridge
import org.craftit.runtime.bytecode_modifiers.ServerBytecodeModifier
import org.craftit.runtime.plugin.PluginLoader
import javax.inject.Inject
import javax.inject.Named

class VanillaServerInitializer @Inject constructor(
    bridge: Bridge,
    bytecodeModifier: ServerBytecodeModifier,
    @Named("server") private val classLoader: ClassLoader,
    pluginLoader: PluginLoader
) :
    ServerInitializer(bridge, bytecodeModifier, pluginLoader) {
    override fun startServer() {
        super.startServer()
        val main = classLoader.loadClass("net.minecraft.server.Main")
        main.getDeclaredMethod("main", Array<String>::class.java).invoke(null, arrayOf("nogui"))
    }
}
