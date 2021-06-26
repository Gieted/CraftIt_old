package org.craftit.runtime.resources.entities.player

import dagger.Module
import dagger.Provides
import org.craftit.api.resources.entities.player.components.OnlineComponent
import org.craftit.api.resources.entities.player.connector.Connector
import org.craftit.api.resources.entities.player.connector.VanillaConnector
import org.craftit.api.resources.entities.player.connector.packet_handler.PacketHandler
import org.craftit.api.resources.entities.player.connector.packet_handler.VanillaPacketHandler
import org.craftit.api.resources.entities.player.input_resolver.InputResolver
import org.craftit.api.resources.entities.player.input_resolver.VanillaInputResolver
import org.craftit.api.resources.entities.player.presenter.Presenter
import javax.inject.Singleton

@Module
abstract class PlayerModule {
    companion object {
        @Provides
        @Singleton
        fun inputResolverFactory(): InputResolver.Factory =
            InputResolver.Factory { player -> VanillaInputResolver(player) }

        @Provides
        @Singleton
        fun rootPresenterFactory(): Presenter.Factory =
            Presenter.Factory { player -> VanillaPresenter(player) }

        @Provides
        @Singleton
        fun connectorFactory(packetHandlerFactory: PacketHandler.Factory): Connector.Factory =
            VanillaConnector.Factory(packetHandlerFactory)

        @Provides
        @Singleton
        fun packetHandlerFactory(): PacketHandler.Factory = PacketHandler.Factory { player -> VanillaPacketHandler(player) }

        @Provides
        @Singleton
        fun onlineComponentFactory(
            inputResolverFactory: InputResolver.Factory,
            presenterFactory: Presenter.Factory,
            connectorFactory: Connector.Factory
        ) = OnlineComponent.Factory(inputResolverFactory, presenterFactory, connectorFactory)
    }
}
