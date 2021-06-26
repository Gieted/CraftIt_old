package org.craftit.api.resources.entities.player

import org.craftit.api.resources.entities.player.connector.NativeConnector

interface NativePlayer {
    var health: Int
    
    val connector: NativeConnector
}
