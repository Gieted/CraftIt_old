package org.craftit.runtime

import dagger.Binds
import dagger.Module
import dagger.Provides
import javassist.ClassPool
import org.craftit.api.Server
import org.craftit.runtime.configuration.Configuration
import org.craftit.runtime.server.ServerComponent
import org.craftit.runtime.server.ServerScope
import org.craftit.runtime.server.VanillaServer
import org.craftit.runtime.source_maps.SourceMap
import org.craftit.runtime.source_maps.vanilla1_16_5SourceMap
import java.io.File
import java.net.URLClassLoader
import java.security.ProtectionDomain
import javax.inject.Named
import javax.inject.Singleton

@Module(subcomponents = [ServerComponent::class])
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
        @Singleton
        @Named("pluginsDirectory")
        fun pluginDirectory(@Named("serverDirectory") serverDirectory: File): File = serverDirectory.resolve("plugins")

        @Provides
        @Singleton
        @Named("serverDirectory")
        fun serverDirectory(): File = File("")

        @Provides
        @Named("server")
        @Singleton
        fun classLoader(configuration: Configuration): ClassLoader =
            URLClassLoader(arrayOf(configuration.serverFile.toURI().toURL()))
    }
    
    @Binds
    @Named("new")
    abstract fun server(to: VanillaServer): Server
}
