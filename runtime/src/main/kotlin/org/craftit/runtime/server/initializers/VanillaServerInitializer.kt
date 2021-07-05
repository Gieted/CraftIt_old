package org.craftit.runtime.server.initializers

import org.craftit.runtime.Bridge
import org.craftit.runtime.bytecode_modifiers.ServerBytecodeModifier
import org.craftit.runtime.server.ServerScope
import javax.inject.Inject

@ServerScope
class VanillaServerInitializer @Inject constructor(
    bridge: Bridge,
    bytecodeModifier: ServerBytecodeModifier,
    private val classLoader: ClassLoader,
) : NativeServerInitializer(bridge, bytecodeModifier) {

    override fun startServer() {
        super.startServer()
        val main = classLoader.loadClass("net.minecraft.server.Main")
        main.getDeclaredMethod("main", Array<String>::class.java).invoke(null, arrayOf("nogui"))
    }
}
