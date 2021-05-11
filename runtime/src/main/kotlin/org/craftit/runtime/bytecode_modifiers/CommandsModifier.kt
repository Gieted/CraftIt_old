package org.craftit.runtime.bytecode_modifiers

import javassist.ClassPool
import org.craftit.runtime.source_maps.SourceMap
import java.security.ProtectionDomain
import javax.inject.Inject
import javax.inject.Named

@Suppress("LocalVariableName")
class CommandsModifier @Inject constructor(
    @Named("server") private val classLoader: ClassLoader,
    private val sourceMap: SourceMap,
    private val classPool: ClassPool,
    private val protectionDomain: ProtectionDomain
) : BytecodeModifier {

    override fun modify() {
        with(sourceMap { net.minecraft.command.Commands }) {
            val serverPlayerEntity = classPool.get(this())!!

            fun modifyFillUsableCommands() {
                val fillUsableCommands = serverPlayerEntity.declaredMethods.find {
                    it.name == fillUsableCommands
                            && it.parameterTypes.size == 4
                            && it.parameterTypes[3] == classPool.get("java.util.Map")
                }!!

                val ServerPlayerEntity = sourceMap { net.minecraft.entity.player.ServerPlayerEntity }()
                with(sourceMap { net.minecraft.command.CommandSource }) {
                    fillUsableCommands.insertAfter(
                        """{
                        org.craftit.api.resources.entities.player.Player player = (($ServerPlayerEntity) $3.$getEntity()).craftItPlayer;
                        if ($2 instanceof com.mojang.brigadier.tree.RootCommandNode) {
                            org.craftit.runtime.Bridge.rootNodeFiller.fillRootNote($2, player);
                        }
                        }"""
                    )
                }
            }

            modifyFillUsableCommands()
            serverPlayerEntity.toClass(classLoader, protectionDomain)
        }
    }
}
