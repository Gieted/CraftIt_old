package org.craftit.runtime

import org.craftit.runtime.resources.commands.RootNoteFiller
import org.craftit.runtime.resources.entities.player.PlayerFactory
import org.craftit.runtime.resources.packets.PacketConverter
import javax.inject.Inject

class Bridge @Inject constructor(
    private val playerFactory: PlayerFactory,
    private val rootNodeFiller: RootNoteFiller,
    private val packetConverter: PacketConverter
) {
    fun setup() {
        Bridge.playerFactory = playerFactory
        Bridge.rootNodeFiller = rootNodeFiller
        Bridge.packetConverter = packetConverter
    }

    companion object {
        @JvmField
        var playerFactory: PlayerFactory? = null

        @JvmField
        var rootNodeFiller: RootNoteFiller? = null

        @JvmField
        var packetConverter: PacketConverter? = null
    }
}
