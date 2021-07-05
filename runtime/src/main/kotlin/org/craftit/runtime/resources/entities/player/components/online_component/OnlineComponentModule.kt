package org.craftit.runtime.resources.entities.player.components.online_component

import dagger.Binds
import dagger.Module
import org.craftit.api.resources.entities.player.connector.Connector
import org.craftit.api.resources.entities.player.connector.packet_handler.PacketHandler
import org.craftit.api.resources.entities.player.input_resolver.InputResolver
import org.craftit.api.resources.entities.player.presenter.Presenter
import org.craftit.runtime.resources.entities.player.VanillaInputResolver
import org.craftit.runtime.resources.entities.player.VanillaPresenter

@Module
abstract class OnlineComponentModule {
    @Binds
    abstract fun inputResolver(to: VanillaInputResolver): InputResolver

    @Binds
    abstract fun presenter(to: VanillaPresenter): Presenter

    @Binds
    abstract fun packetHandler(to: VanillaPacketHandler): PacketHandler

    @Binds
    abstract fun connectorFactory(to: VanillaConnector.Factory): Connector.Factory
}
