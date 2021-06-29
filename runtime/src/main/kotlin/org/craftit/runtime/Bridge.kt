package org.craftit.runtime

import org.craftit.api.Server
import org.craftit.api.resources.entities.player.PlayerRegistry
import org.craftit.runtime.resources.commands.RootNoteFiller
import org.craftit.runtime.resources.packets.converters.PacketConverter
import org.craftit.runtime.server.ServerScope
import javax.inject.Inject

@ServerScope
class Bridge @Inject constructor(
    private val rootNodeFiller: RootNoteFiller,
    private val packetConverter: PacketConverter,
    private val server: Server
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
        var players: PlayerRegistry? = null
    }
}
