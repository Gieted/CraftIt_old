package org.craftit.runtime.bytecode_modifiers

import javassist.ClassPool
import javassist.CtMethod
import org.craftit.runtime.source_maps.SourceMap
import java.security.ProtectionDomain
import javax.inject.Inject
import javax.inject.Named

class ServerPlayNetHandlerModifier @Inject constructor(
    @Named("server") private val classLoader: ClassLoader,
    private val sourceMap: SourceMap,
    private val classPool: ClassPool,
    private val protectionDomain: ProtectionDomain
) : BytecodeModifier {

    override fun modify() {
        with(sourceMap { net.minecraft.network.play.ServerPlayNetHandler }) {
            val serverPlayNetHandler = classPool.get(this())

            fun modifyHandleChat() {
                val handleChat =
                    serverPlayNetHandler.getDeclaredMethod(
                        handleChat,
                        arrayOf(classPool.get(sourceMap { net.minecraft.network.play.client.CChatMessagePacket }()))
                    )

                val handleChatCopy = CtMethod(handleChat, serverPlayNetHandler, null)
                handleChatCopy.name = "vanillaHandleChat"

                serverPlayNetHandler.addMethod(handleChatCopy)

                handleChat.setBody("""this.$player.craftItPlayer.getPacketResolver().onChatMessage(org.craftit.runtime.Bridge.packetConverter.convertCChatMessage($1));""")
            }

            modifyHandleChat()
            serverPlayNetHandler.toClass(classLoader, protectionDomain)
        }
    }
}
