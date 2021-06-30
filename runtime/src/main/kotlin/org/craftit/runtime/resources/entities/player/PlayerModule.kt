package org.craftit.runtime.resources.entities.player

import dagger.Binds
import dagger.Module
import dagger.Provides
import org.craftit.api.resources.entities.player.connector.packet_handler.PacketHandler
import org.craftit.api.resources.entities.player.controller.PlayerController
import org.craftit.api.resources.entities.player.input_resolver.InputResolver
import org.craftit.api.resources.entities.player.input_resolver.VanillaInputResolver
import org.craftit.api.resources.entities.player.presenter.Presenter
import org.craftit.runtime.resources.entities.player.components.online_component.VanillaPacketHandler
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
        fun packetHandlerFactory(): PacketHandler.Factory =
            PacketHandler.Factory { player -> VanillaPacketHandler(player) }
    }
    
    @Binds
    @PlayerScope
    abstract fun playerController(to: VanillaPlayerController): PlayerController
}
