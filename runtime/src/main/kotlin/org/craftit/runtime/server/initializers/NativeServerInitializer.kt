package org.craftit.runtime.server.initializers

import org.craftit.runtime.Bridge
import org.craftit.runtime.bytecode_modifiers.ServerBytecodeModifier

abstract class NativeServerInitializer(
    private val bridge: Bridge,
    private val bytecodeModifier: ServerBytecodeModifier,
) {
    open fun startServer() {
        bridge.setup()
        bytecodeModifier.modify()
    }
}
