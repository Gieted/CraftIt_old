package org.craftit.runtime.resources.entities.player

import org.craftit.api.resources.entities.player.Player
import org.craftit.api.resources.entities.player.presenter.Presenter
import org.craftit.runtime.resources.entities.player.components.online_component.OnlineComponentScope
import javax.inject.Inject

@OnlineComponentScope
class VanillaPresenter @Inject constructor(private val player: Player) : Presenter {
    override fun present(): Presenter.Model {
        TODO()
    }
}
