package org.craftit.runtime.bytecode_modifiers

import javassist.ClassPool
import javassist.CtMethod
import org.craftit.runtime.server.ServerScope
import org.craftit.runtime.source_maps.SourceMap
import java.security.ProtectionDomain
import javax.inject.Inject

@ServerScope
class ServerPlayNetHandlerModifier @Inject constructor(
    private val classLoader: ClassLoader,
    private val sourceMap: SourceMap,
    private val classPool: ClassPool,
    private val protectionDomain: ProtectionDomain
) : BytecodeModifier {

    override fun modify() {
        with(sourceMap { net.minecraft.network.play.ServerPlayNetHandler }) {
            val serverPlayNetHandler = classPool.get(this())
            @Suppress("LocalVariableName") val Bridge = "org.craftit.runtime.Bridge"

            fun modifyHandleChat() {
                val handleChat =
                    serverPlayNetHandler.getDeclaredMethod(
                        handleChat,
                        arrayOf(classPool.get(sourceMap { net.minecraft.network.play.client.CChatMessagePacket }()))
                    )

                val handleChatCopy = CtMethod(handleChat, serverPlayNetHandler, null)
                handleChatCopy.name = "nativeHandleChat"

                serverPlayNetHandler.addMethod(handleChatCopy)

                with(sourceMap { net.minecraft.entity.player.ServerPlayerEntity }) {
                    handleChat.setBody(
                        """$Bridge.getConnector(this.$player.$getUUID()).notify($Bridge.packetConverter.convertCChatMessage($1));"""
                    )
                }
            }

            fun modifyConstructor() {
                val constructor = serverPlayNetHandler.constructors.find { it.parameterTypes.isNotEmpty() }!!

                with(sourceMap { net.minecraft.entity.player.ServerPlayerEntity }) {
                    constructor.insertAfter(
                        """
                    $Bridge.onServerPlayerEntityUpdate($3.$getUUID(), $3, this);
                            """
                    )
                }
            }

            modifyHandleChat()
            modifyConstructor()
            serverPlayNetHandler.toClass(classLoader, protectionDomain)
        }
    }
}
