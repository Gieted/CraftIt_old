package org.craftit.runtime

import org.craftit.runtime.resources.commands.RootNoteFiller
import org.craftit.runtime.resources.entities.player.NativeConnectorCache
import org.craftit.runtime.resources.entities.player.PlayerFactory
import org.craftit.runtime.resources.packets.converters.PacketConverter
import javax.inject.Inject

class Bridge @Inject constructor(
    private val playerFactory: PlayerFactory,
    private val rootNodeFiller: RootNoteFiller,
    private val packetConverter: PacketConverter,
    private val nativeConnectorCache: NativeConnectorCache
) {
    fun setup() {
        Bridge.playerFactory = playerFactory
        Bridge.rootNodeFiller = rootNodeFiller
        Bridge.packetConverter = packetConverter
        connectors = nativeConnectorCache
    }

    companion object {
        @JvmField
        var playerFactory: PlayerFactory? = null

        @JvmField
        var rootNodeFiller: RootNoteFiller? = null

        @JvmField
        var packetConverter: PacketConverter? = null

        @JvmField
        var connectors: NativeConnectorCache? = null
    }
}
