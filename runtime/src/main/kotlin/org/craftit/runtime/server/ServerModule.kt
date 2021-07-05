package org.craftit.runtime.server

import dagger.Binds
import dagger.Module
import dagger.Provides
import org.craftit.api.resources.IdGenerator
import org.craftit.api.resources.commands.Command
import org.craftit.api.resources.commands.CommandParser
import org.craftit.api.resources.commands.CommandRegistry
import org.craftit.api.resources.commands.VanillaCommandParser
import org.craftit.api.resources.entities.EntityRegistry
import org.craftit.api.resources.entities.player.Player
import org.craftit.api.resources.entities.player.PlayerRegistry
import org.craftit.api.resources.entities.player.components.PlayerComponentsRegistry
import org.craftit.api.resources.entities.player.components.online.OnlineComponent
import org.craftit.api.resources.entities.player.components.online.OnlineComponentRegistry
import org.craftit.api.resources.plugin.PluginRegistry
import org.craftit.runtime.configuration.Configuration
import org.craftit.runtime.resources.VanillaIdGenerator
import org.craftit.runtime.resources.commands.RootCommand
import org.craftit.runtime.resources.commands.VanillaCommandRegistry
import org.craftit.runtime.resources.entities.VanillaEntityRegistry
import org.craftit.runtime.resources.entities.player.PlayerDaggerComponent
import org.craftit.runtime.resources.entities.player.VanillaPlayer
import org.craftit.runtime.resources.entities.player.VanillaPlayerRegistry
import org.craftit.runtime.resources.entities.player.components.VanillaPlayerComponentsRegistry
import org.craftit.runtime.resources.entities.player.components.online_component.OnlineComponentComponent
import org.craftit.runtime.resources.entities.player.components.online_component.VanillaOnlineComponent
import org.craftit.runtime.resources.entities.player.components.online_component.VanillaOnlineComponentRegistry
import org.craftit.runtime.resources.plugin.VanillaPluginRegistry
import org.craftit.runtime.server.initializers.NativeServerInitializer
import org.craftit.runtime.server.initializers.VanillaServerInitializer
import java.net.URLClassLoader
import javax.inject.Named

@Module(subcomponents = [PlayerDaggerComponent::class, OnlineComponentComponent::class])
abstract class ServerModule {
    companion object {
        @Provides
        @ServerScope
        fun classLoader(configuration: Configuration): ClassLoader =
            URLClassLoader(arrayOf(configuration.serverFile.toURI().toURL()))
    }

    @Binds
    @ServerScope
    abstract fun entityRegistry(to: VanillaEntityRegistry): EntityRegistry

    @Binds
    @ServerScope
    abstract fun commandRegistry(to: VanillaCommandRegistry): CommandRegistry

    @Binds
    @ServerScope
    abstract fun pluginRegistry(to: VanillaPluginRegistry): PluginRegistry

    @Binds
    @ServerScope
    abstract fun nativeServerInitializer(to: VanillaServerInitializer): NativeServerInitializer

    @Binds
    @ServerScope
    abstract fun commandParser(to: VanillaCommandParser): CommandParser
    
    @Binds
    @ServerScope
    @Named("root")
    abstract fun rootCommand(to: RootCommand): Command

    @Binds
    @ServerScope
    abstract fun idGenerator(to: VanillaIdGenerator): IdGenerator

    @Binds
    abstract fun playerRegistry(to: VanillaPlayerRegistry): PlayerRegistry

    @Binds
    abstract fun playerComponentsRegistry(to: VanillaPlayerComponentsRegistry): PlayerComponentsRegistry
    
    @Binds
    abstract fun onlineComponentRegistry(to: VanillaOnlineComponentRegistry): OnlineComponentRegistry
    
    @Binds
    abstract fun playerFactory(to: VanillaPlayer.Factory): Player.Factory
    
    @Binds
    abstract fun onlineComponentFactory(to: VanillaOnlineComponent.Factory): OnlineComponent.Factory
}
