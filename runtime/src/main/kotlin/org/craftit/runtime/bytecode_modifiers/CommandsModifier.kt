package org.craftit.runtime.bytecode_modifiers

import javassist.ClassPool
import org.craftit.runtime.server.ServerScope
import org.craftit.runtime.source_maps.SourceMap
import java.security.ProtectionDomain
import javax.inject.Inject

@Suppress("LocalVariableName")
@ServerScope
class CommandsModifier @Inject constructor(
    private val classLoader: ClassLoader,
    private val sourceMap: SourceMap,
    private val classPool: ClassPool,
    private val protectionDomain: ProtectionDomain
) : BytecodeModifier {

    override fun modify() {
        with(sourceMap { net.minecraft.command.Commands }) {
            val serverPlayerEntity = classPool.get(this())

            fun modifyFillUsableCommands() {
                val fillUsableCommands = serverPlayerEntity.declaredMethods.find {
                    it.name == fillUsableCommands
                            && it.parameterTypes.size == 4
                            && it.parameterTypes[3] == classPool.get("java.util.Map")
                }!!
                
                val Bridge = "org.craftit.runtime.Bridge"

                val ServerPlayerEntity = sourceMap { net.minecraft.entity.player.ServerPlayerEntity }()
                with(sourceMap { net.minecraft.command.CommandSource }) {
                    with(sourceMap { net.minecraft.entity.player.ServerPlayerEntity }) {
                        fillUsableCommands.setBody(
                            """{
                        org.craftit.api.resources.entities.player.Player player = $Bridge.getPlayer((($ServerPlayerEntity) $3.$getEntity()).$getUUID());
                        $Bridge.rootNodeFiller.fillRootNote($2, player);
                        }"""
                        )
                    }
                }
            }

            modifyFillUsableCommands()
            serverPlayerEntity.toClass(classLoader, protectionDomain)
        }
    }
}
