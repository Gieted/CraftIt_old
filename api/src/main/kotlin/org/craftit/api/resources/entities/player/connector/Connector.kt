package org.craftit.api.resources.entities.player.connector


interface Connector {

    fun interface Factory {
        fun create(nativeConnector: NativeConnector): Connector
    }
    
    fun connect()
    
    fun sendUpdates()
}
