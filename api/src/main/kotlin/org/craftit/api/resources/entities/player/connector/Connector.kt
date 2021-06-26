package org.craftit.api.resources.entities.player.connector

import org.craftit.api.resources.entities.player.Player

interface Connector {

    fun interface Factory {
        fun create(player: Player, nativeConnector: NativeConnector): Connector
    }
    
    fun start()
    
    fun sendUpdates()
}
