package org.craftit.runtime.resources.entities.player.components

import org.craftit.api.resources.entities.player.components.PlayerComponentsRegistry
import org.craftit.api.resources.entities.player.components.online.OnlineComponentRegistry
import org.craftit.runtime.server.ServerScope
import javax.inject.Inject

@ServerScope
class VanillaPlayerComponentsRegistry @Inject constructor(
    override val online: OnlineComponentRegistry
) : PlayerComponentsRegistry
