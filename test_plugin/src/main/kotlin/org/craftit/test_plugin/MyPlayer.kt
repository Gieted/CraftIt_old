package org.craftit.test_plugin

import org.craftit.api.resources.entities.player.Player

class MyPlayer(private val vanillaPlayer: Player): Player by vanillaPlayer {

}
