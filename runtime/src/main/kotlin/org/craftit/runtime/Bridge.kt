package org.craftit.runtime

import org.craftit.runtime.resources.commands.RootNoteFiller
import org.craftit.runtime.resources.entities.player.RuntimePlayerRegistry
import org.craftit.runtime.resources.packets.converters.PacketConverter
import javax.inject.Inject

class Bridge @Inject constructor(
    private val rootNodeFiller: RootNoteFiller,
    private val packetConverter: PacketConverter,
    private val server: RuntimeServer
    ) {
    fun setup() {
        Bridge.rootNodeFiller = rootNodeFiller
        Bridge.packetConverter = packetConverter
        players = server.entities.players
    }

    companion object {
        @JvmField
        var rootNodeFiller: RootNoteFiller? = null

        @JvmField
        var packetConverter: PacketConverter? = null

        @JvmField
        var players: RuntimePlayerRegistry? = null
    }
}
