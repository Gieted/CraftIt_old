package org.craftit.api.resources.entities.player.components.online

import org.craftit.api.resources.entities.player.NativePlayer
import org.craftit.api.resources.entities.player.Player

interface OnlineComponentRegistry: OnlineComponent.Factory {
    var factory: OnlineComponent.Factory

    override fun create(player: Player, nativePlayer: NativePlayer): OnlineComponent
}
