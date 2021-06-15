package org.craftit.runtime.resources.entities.player

import dagger.Module
import dagger.Provides
import org.craftit.api.Server
import org.craftit.api.resources.entities.player.VanillaInputResolver
import org.craftit.api.resources.entities.player.VanillaPacketResolver
import org.craftit.api.resources.entities.player.VanillaPlayer
import org.craftit.api.resources.entities.player.VanillaPlayerController
import javax.inject.Singleton

@Module
class PlayerModule {

    @Provides
    @Singleton
    fun vanillaPacketResolverFactory() = VanillaPacketResolver.Factory()

    @Provides
    @Singleton
    fun vanillaPlayerFactory(
        inputResolverFactory: VanillaInputResolver.Factory,
        controllerFactory: VanillaPlayerController.Factory,
        packetResolverFactory: VanillaPacketResolver.Factory,
        server: Server
    ) = VanillaPlayer.Factory(inputResolverFactory, controllerFactory, packetResolverFactory, server)
}
