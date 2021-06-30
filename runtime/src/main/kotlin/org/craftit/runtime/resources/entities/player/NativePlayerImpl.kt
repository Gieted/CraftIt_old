package org.craftit.runtime.resources.entities.player

import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import org.craftit.api.resources.entities.player.connector.NativeConnector
import org.craftit.api.resources.entities.player.NativePlayer
import org.craftit.runtime.server.ServerScope
import org.craftit.runtime.source_maps.SourceMap
import org.craftit.runtime.text.StringTextComponentFactory
import java.lang.reflect.Method
import java.util.*

class NativePlayerImpl @AssistedInject constructor(
    @Assisted private val serverPlayerEntity: Any,
    @Assisted override val connector: NativeConnector,
    sourceMap: SourceMap,
    private val stringTextComponentFactory: StringTextComponentFactory,
//    private val chatType: ChatType,
//    private val nativeConnectorCache: NativeConnectorCache
) : NativePlayer {
    
    @AssistedFactory
    interface Factory {
        fun create(serverPlayerEntity: Any, nativeConnector: NativeConnector): NativePlayerImpl
    }

    private val getHealthMethod: Method
    private val setHealthMethod: Method
    private val sendMessageMethod: Method

    init {
        val serverPlayerEntityClass = serverPlayerEntity::class.java
        val livingEntityClass = serverPlayerEntityClass.superclass.superclass

        with(sourceMap { net.minecraft.entity.LivingEntity }) {
            getHealthMethod = livingEntityClass.getDeclaredMethod(getHealth)
            setHealthMethod = livingEntityClass.getDeclaredMethod(setHealth, Float::class.java)
        }

        with(sourceMap { net.minecraft.entity.player.ServerPlayerEntity }) {
            sendMessageMethod =
                serverPlayerEntityClass.declaredMethods.find {
                    it.name == sendMessage
                            && it.parameterCount == 3
                            && it.parameterTypes[2] == UUID::class.java
                }!!
        }
    }

    override var health: Int
        get() = (getHealthMethod.invoke(serverPlayerEntity) as Float).toInt()
        set(value) {
            setHealthMethod.invoke(serverPlayerEntity, value.toFloat())
        }
}
