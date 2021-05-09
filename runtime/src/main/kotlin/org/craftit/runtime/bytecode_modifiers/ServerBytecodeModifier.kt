package org.craftit.runtime.bytecode_modifiers

import javax.inject.Inject

class ServerBytecodeModifier @Inject constructor(
    private val serverPlayerEntityModifier: ServerPlayerEntityModifier,
    private val serverPlayNetHandlerModifier: ServerPlayNetHandlerModifier,
    private val commandsModifier: CommandsModifier
) : BytecodeModifier {
    override fun modify() {
        serverPlayerEntityModifier.modify()
        serverPlayNetHandlerModifier.modify()
        commandsModifier.modify()
    }
}
