package org.craftit.runtime

import org.craftit.api.Server
import org.craftit.api.resources.entities.player.components.online.OnlineComponent
import org.craftit.runtime.resources.commands.RootNoteFiller
import org.craftit.runtime.resources.entities.player.NativeConnectorImpl
import org.craftit.runtime.resources.entities.player.NativePlayerImpl
import org.craftit.runtime.resources.entities.player.components.RuntimePlayerComponent
import org.craftit.runtime.resources.packets.converters.PacketConverter
import org.craftit.runtime.server.ServerScope
import java.util.*
import javax.inject.Inject

@ServerScope
class Bridge @Inject constructor(
    private val rootNodeFiller: RootNoteFiller,
    private val packetConverter: PacketConverter,
    private val server: Server,
    private val nativeConnectorFactory: NativeConnectorImpl.Factory,
    private val nativePlayerFactory: NativePlayerImpl.Factory,
    private val runtimePlayerComponentFactory: RuntimePlayerComponent.Factory
) {
    fun setup() {
        bridge = this
        Bridge.rootNodeFiller = rootNodeFiller
        Bridge.packetConverter = packetConverter
    }

    fun getConnectorM(playerUUID: UUID) =
        server.entities.players[playerUUID.toString()]!!.components.require(RuntimePlayerComponent::class).nativeConnector

    private fun onServerPlayerEntityUpdateM(uuid: UUID, serverPlayerEntity: Any, playNetHandler: Any) {
        val player = server.entities.players.getOrCreate(uuid.toString())
        
        val nativeConnector = nativeConnectorFactory.create(playNetHandler)
        val nativePlayer = nativePlayerFactory.create(serverPlayerEntity, nativeConnector)

        player.components.attach(server.entities.players.components.onlineComponent.create(player, nativePlayer))
        player.components.attach(runtimePlayerComponentFactory.create(nativeConnector))
    }

    private fun onPlayerDisconnectM(uuid: UUID) {
        val player = server.entities.players.getOrCreate(uuid.toString())
        player.components.removeAll(OnlineComponent::class)
        player.components.removeAll(RuntimePlayerComponent::class)
    }

    private fun getPlayerM(uuid: UUID) = server.entities.players[uuid.toString()]

    companion object {
        @JvmField
        var rootNodeFiller: RootNoteFiller? = null

        @JvmField
        var packetConverter: PacketConverter? = null

        private var bridge: Bridge? = null

        @JvmStatic
        fun getConnector(playerUUID: UUID) = bridge!!.getConnectorM(playerUUID)

        @JvmStatic
        fun onServerPlayerEntityUpdate(uuid: UUID, serverPlayerEntity: Any, playNetHandler: Any) =
            bridge!!.onServerPlayerEntityUpdateM(uuid, serverPlayerEntity, playNetHandler)

        @JvmStatic
        fun onPlayerDisconnect(uuid: UUID) = bridge!!.onPlayerDisconnectM(uuid)

        @JvmStatic
        fun getPlayer(uuid: UUID) = bridge!!.getPlayerM(uuid)
    }
}
