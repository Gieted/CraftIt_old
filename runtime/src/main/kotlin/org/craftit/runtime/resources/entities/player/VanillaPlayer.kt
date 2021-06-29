package org.craftit.runtime.resources.entities.player

import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import org.craftit.api.*
import org.craftit.api.chat.ChatParticipant
import org.craftit.api.resources.components.ComponentStore
import org.craftit.api.resources.entities.player.Player
import org.craftit.api.resources.entities.player.components.PlayerComponent
import org.craftit.api.resources.entities.player.controller.VanillaPlayerController
import org.craftit.runtime.resources.entities.player.components.online_component.VanillaOnlineComponent
import java.util.*

class VanillaPlayer @AssistedInject constructor(
    controllerFactory: VanillaPlayerController.Factory,
    @Assisted override val server: Server,
    @Assisted override val uuid: UUID
) : Player {
    
    @AssistedFactory
    interface Factory {
        fun create(uuid: UUID, server: Server): VanillaPlayer
    }
    
    override val controller: VanillaPlayerController = controllerFactory.create(this)
    
    override val components: ComponentStore<PlayerComponent> = ComponentStore()
    override val id: String
        get() = uuid.toString()

    override fun sendMessage(content: Text) {
        components[VanillaOnlineComponent::class]?.sendMessage(content)
    }

    override fun sendMessage(content: Text, sender: ChatParticipant) {
        components[VanillaOnlineComponent::class]?.sendMessage(content, sender)
    }

    override var health: Int
        get() = components[VanillaOnlineComponent::class]!!.nativePlayer.health
        set(value) {
            components[VanillaOnlineComponent::class]!!.nativePlayer.health = value
        }
}
