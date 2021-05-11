package org.craftit.runtime.bytecode_modifiers

import javassist.ClassPool
import javassist.CtField
import org.craftit.runtime.source_maps.SourceMap
import java.security.ProtectionDomain
import javax.inject.Inject
import javax.inject.Named

class ServerPlayerEntityModifier @Inject constructor(
    @Named("server") private val classLoader: ClassLoader,
    private val sourceMap: SourceMap,
    private val classPool: ClassPool,
    private val protectionDomain: ProtectionDomain
) : BytecodeModifier {

    override fun modify() {
        with(sourceMap { net.minecraft.entity.player.ServerPlayerEntity }) {
            val serverPlayerEntity = classPool.get(this())!!

            fun addCraftItPlayerField() {
                val craftItPlayerField = CtField.make(
                    """org.craftit.api.resources.entities.player.Player craftItPlayer = org.craftit.runtime.Bridge.playerFactory.create(this);""",
                    serverPlayerEntity
                )
                serverPlayerEntity.addField(craftItPlayerField)
            }

            addCraftItPlayerField()
            serverPlayerEntity.toClass(classLoader, protectionDomain)
        }
    }
}
