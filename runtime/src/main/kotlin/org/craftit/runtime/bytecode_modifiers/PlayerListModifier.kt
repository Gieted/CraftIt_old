package org.craftit.runtime.bytecode_modifiers

import javassist.ClassPool
import javassist.CtMethod
import org.craftit.runtime.server.ServerScope
import org.craftit.runtime.source_maps.SourceMap
import java.security.ProtectionDomain
import javax.inject.Inject

@Suppress("LocalVariableName")
@ServerScope
class PlayerListModifier @Inject constructor(
    private val classLoader: ClassLoader,
    private val sourceMap: SourceMap,
    private val classPool: ClassPool,
    private val protectionDomain: ProtectionDomain
) : BytecodeModifier {

    override fun modify() {
        with(sourceMap { net.minecraft.server.management.PlayerList }) {
            val playerList = classPool.get(this())

            fun modifyRespawn() {
                val respawn = playerList.declaredMethods.find {
                    it.name == respawn && it.parameterTypes.size == 2 && it.parameterTypes[1] == classPool.get("boolean")
                }!!

                val nativeRespawn = CtMethod(respawn, playerList, null)
                nativeRespawn.name = "nativeRespawn"

                playerList.addMethod(nativeRespawn)

                val Bridge = "org.craftit.runtime.Bridge"

                with(sourceMap { net.minecraft.entity.player.ServerPlayerEntity }) {
                    val ServerPlayerEntity = this()
                    
                    respawn.setBody(
                        """{
                $ServerPlayerEntity player = nativeRespawn($1, $2);
                $Bridge.onServerPlayerEntityUpdate(player.$getUUID(), player, player.$connection);
                
                return player;
                        }"""
                    )
                }
            }

            modifyRespawn()
            playerList.toClass(classLoader, protectionDomain)
        }
    }
}
