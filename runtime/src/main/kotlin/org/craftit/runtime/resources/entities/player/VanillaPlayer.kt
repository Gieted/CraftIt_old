package org.craftit.runtime.resources.entities.player

import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import org.craftit.api.Server
import org.craftit.api.text.Text
import org.craftit.api.chat.ChatParticipant
import org.craftit.api.resources.components.ComponentStore
import org.craftit.api.resources.entities.player.Player
import org.craftit.api.resources.entities.player.components.PlayerComponent
import org.craftit.api.resources.entities.player.components.online.OnlineComponent
import org.craftit.api.resources.entities.player.controller.PlayerController
import org.craftit.runtime.resources.entities.player.components.online_component.VanillaOnlineComponent

class VanillaPlayer @AssistedInject constructor(
    playerComponentFactory: PlayerDaggerComponent.Factory,
    override val server: Server,
    @Assisted override val id: String
) : Player {
    
    @AssistedFactory
    interface Factory: Player.Factory {
        override fun create(id: String): VanillaPlayer
    }
    
    override val controller: PlayerController
    
    override val components: ComponentStore<PlayerComponent> = ComponentStore()

    override fun sendMessage(content: Text, sender: ChatParticipant?) {
        components[OnlineComponent::class]?.sendMessage(content, sender)
    }

    init {
        val component = playerComponentFactory.create(this)
        controller = component.controller()
    }

    override var health: Int
        get() = components[VanillaOnlineComponent::class]!!.nativePlayer.health
        set(value) {
            components[VanillaOnlineComponent::class]!!.nativePlayer.health = value
        }
}
