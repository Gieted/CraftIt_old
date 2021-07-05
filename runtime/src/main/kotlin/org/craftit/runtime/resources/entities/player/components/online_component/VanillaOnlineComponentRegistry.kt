package org.craftit.runtime.resources.entities.player.components.online_component

import org.craftit.api.resources.entities.player.NativePlayer
import org.craftit.api.resources.entities.player.Player
import org.craftit.api.resources.entities.player.components.online.OnlineComponent
import org.craftit.api.resources.entities.player.components.online.OnlineComponentRegistry
import org.craftit.runtime.server.ServerScope
import javax.inject.Inject

@ServerScope
class VanillaOnlineComponentRegistry @Inject constructor(
    override var factory: OnlineComponent.Factory
) : OnlineComponentRegistry {

    override fun create(player: Player, nativePlayer: NativePlayer): OnlineComponent = factory.create(player, nativePlayer)
}
