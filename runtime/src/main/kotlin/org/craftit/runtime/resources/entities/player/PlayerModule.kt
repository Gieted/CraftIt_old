package org.craftit.runtime.resources.entities.player

import dagger.Binds
import dagger.Module
import dagger.Provides
import org.craftit.api.Server
import org.craftit.api.resources.entities.player.*
import javax.inject.Singleton

@Module
abstract class PlayerModule {
    companion object {
        @Provides
        @Singleton
        fun vanillaPacketResolverFactory() = VanillaPacketHandler.Factory()

        @Provides
        @Singleton
        fun vanillaPlayerFactory(
            inputResolverFactory: VanillaInputResolver.Factory,
            controllerFactory: VanillaPlayerController.Factory,
            packetHandlerFactory: VanillaPacketHandler.Factory,
            server: Server,
            presenter: PlayerPresenter
        ) = VanillaPlayer.Factory(inputResolverFactory, controllerFactory, packetHandlerFactory, server, presenter)
        
        @Provides
        fun vanillaPresenter() = VanillaPlayerPresenter()
    }
    
    @Binds
    abstract fun presenter(to: VanillaPlayerPresenter): PlayerPresenter
}
