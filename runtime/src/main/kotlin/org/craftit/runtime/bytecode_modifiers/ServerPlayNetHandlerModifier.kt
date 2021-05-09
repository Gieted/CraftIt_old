package org.craftit.runtime.bytecode_modifiers

import javassist.ClassPool
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
            val serverPlayNetHandler = classPool.get(this())!!

            fun modifyHandleChat() {
                val handleChat =
                    serverPlayNetHandler.getDeclaredMethod(handleChat, arrayOf(classPool.get("java.lang.String")))

                with(sourceMap { net.minecraft.entity.player.ServerPlayerEntity }) {
                    handleChat.setBody(
                        """{
                        this.$player.$resetLastActionTime();
                        this.$player.craftItPlayer.getInputResolver().onChat($1);
                        }"""
                    )
                }
            }

            modifyHandleChat()
            serverPlayNetHandler.toClass(classLoader, protectionDomain)
        }
    }
}
