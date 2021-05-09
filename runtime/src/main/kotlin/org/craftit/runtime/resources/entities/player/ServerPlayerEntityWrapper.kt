package org.craftit.runtime.resources.entities.player

import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import org.craftit.api.ChatParticipant
import org.craftit.api.resources.entities.player.NativePlayer
import org.craftit.runtime.source_maps.SourceMap
import java.lang.reflect.Method
import java.util.*

class ServerPlayerEntityWrapper @AssistedInject constructor(
    @Assisted private val serverPlayerEntity: Any,
    sourceMap: SourceMap,
    private val stringTextComponentFactory: StringTextComponentFactory,
    private val chatType: ChatType
) : NativePlayer {
    @AssistedFactory
    interface Factory {
        fun create(serverPlayerEntity: Any): ServerPlayerEntityWrapper
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

    override fun sendMessage(content: String) {
        sendMessageMethod.invoke(
            serverPlayerEntity,
            stringTextComponentFactory.create(content),
            chatType.SYSTEM,
            UUID(0, 0)
        )
    }

    override fun sendMessage(content: String, sender: ChatParticipant) {
        sendMessageMethod.invoke(
            serverPlayerEntity,
            stringTextComponentFactory.create(content),
            chatType.CHAT,
            UUID(0, 0)
        )
    }
}
