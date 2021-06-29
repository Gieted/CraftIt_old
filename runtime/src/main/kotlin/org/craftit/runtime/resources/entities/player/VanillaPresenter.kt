package org.craftit.runtime.resources.entities.player

import org.craftit.api.resources.entities.player.Player
import org.craftit.api.resources.entities.player.presenter.Presenter
import org.craftit.runtime.resources.entities.player.components.online_component.VanillaOnlineComponent

class VanillaPresenter(private val player: Player) : Presenter {
    override fun present(): Presenter.Model {
        val onlineComponent = player.components[VanillaOnlineComponent::class]

        val messages = run {
            if (onlineComponent != null) {
                val messages = onlineComponent.messageQueue.toList()

                onlineComponent.messageQueue.clear()

                messages
            } else emptyList()
        }

        return Presenter.Model(messages)
    }
}
