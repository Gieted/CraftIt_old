package org.craftit.runtime

import dagger.Binds
import dagger.Module
import dagger.Provides
import javassist.ClassPool
import org.craftit.api.Server
import org.craftit.api.resources.commands.Command
import org.craftit.api.resources.commands.CommandParser
import org.craftit.api.resources.commands.VanillaCommandParser
import org.craftit.runtime.configuration.Configuration
import org.craftit.runtime.configuration.ConfigurationModule
import org.craftit.runtime.resources.ResourcesModule
import org.craftit.runtime.resources.commands.RootCommand
import org.craftit.runtime.server.ServerComponent
import org.craftit.runtime.server.ServerModule
import org.craftit.runtime.server.VanillaServer
import org.craftit.runtime.source_maps.SourceMap
import org.craftit.runtime.source_maps.vanilla1_16_5SourceMap
import java.io.File
import java.net.URLClassLoader
import java.security.ProtectionDomain
import javax.inject.Named
import javax.inject.Singleton

@Module(includes = [ResourcesModule::class], subcomponents = [ServerComponent::class])
abstract class MainModule {
    companion object {
        @Provides
        @Singleton
        fun sourceMap(): SourceMap {
            return vanilla1_16_5SourceMap
        }

        @Provides
        @Singleton
        fun classPool(configuration: Configuration): ClassPool {
            val classPool = ClassPool.getDefault()
            classPool.appendClassPath(configuration.serverFile.absolutePath)

            return classPool
        }

        @Provides
        @Singleton
        fun protectionDomain(): ProtectionDomain = this::class.java.protectionDomain

        @Provides
        @Named("pluginsDirectory")
        fun pluginDirectory(@Named("serverDirectory") serverDirectory: File): File = serverDirectory.resolve("plugins")

        @Provides
        @Singleton
        @Named("serverDirectory")
        fun serverDirectory(): File = File("")
    }

    @Binds
    abstract fun commandParser(to: VanillaCommandParser): CommandParser
    
    @Binds
    @Named("root")
    abstract fun rootCommand(to: RootCommand): Command
    
    @Binds
    @Named("new")
    abstract fun server(to: VanillaServer): Server
}
