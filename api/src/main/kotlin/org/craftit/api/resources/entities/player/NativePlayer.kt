package org.craftit.api.resources.entities.player

interface NativePlayer {
    var health: Int
    
    val connector: NativeConnector
}
