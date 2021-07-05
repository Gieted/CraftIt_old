package org.craftit.runtime.bytecode_modifiers

import javassist.ClassPool
import org.craftit.runtime.server.ServerScope
import org.craftit.runtime.source_maps.SourceMap
import java.security.ProtectionDomain
import javax.inject.Inject

@Suppress("LocalVariableName")
@ServerScope
class ServerPlayerEntityModifier @Inject constructor(
    private val classLoader: ClassLoader,
    private val sourceMap: SourceMap,
    private val classPool: ClassPool,
    private val protectionDomain: ProtectionDomain
) : BytecodeModifier {

    override fun modify() {
        with(sourceMap { net.minecraft.entity.player.ServerPlayerEntity }) {
            val serverPlayerEntity = classPool.get(this())

            fun modifyDisconnect() {
                val disconnect = serverPlayerEntity.getDeclaredMethod(disconnect)
                disconnect.insertAfter(
                    """
                    org.craftit.runtime.Bridge.onPlayerDisconnect($getUUID());
                        """
                )
            }

            modifyDisconnect()
            serverPlayerEntity.toClass(classLoader, protectionDomain)
        }
    }
}
