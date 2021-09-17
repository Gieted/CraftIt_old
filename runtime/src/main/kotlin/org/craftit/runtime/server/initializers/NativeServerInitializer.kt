package org.craftit.runtime.server.initializers

import org.craftit.runtime.Bridge
import org.craftit.runtime.bytecode_modifiers.MainBytecodeModifier

abstract class NativeServerInitializer(
    private val bridge: Bridge,
    private val bytecodeModifier: MainBytecodeModifier,
) {
    open fun startServer() {
        bridge.setup()
        bytecodeModifier.modify()
    }
}
