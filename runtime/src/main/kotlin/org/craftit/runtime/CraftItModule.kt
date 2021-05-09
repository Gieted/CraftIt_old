package org.craftit.runtime

import dagger.Binds
import dagger.Module
import dagger.Provides
import javassist.ClassPool
import jdk.jfr.Name
import org.craftit.api.CraftIt
import org.craftit.api.Server
import org.craftit.api.resources.commands.CommandParser
import org.craftit.api.resources.commands.VanillaCommandParser
import org.craftit.runtime.bytecode_modifiers.ServerBytecodeModifier
import org.craftit.runtime.configuration.Configuration
import org.craftit.runtime.plugin.PluginLoader
import org.craftit.runtime.server_initializer.ServerInitializer
import org.craftit.runtime.server_initializer.VanillaServerInitializer
import org.craftit.runtime.source_maps.SourceMap
import org.craftit.runtime.source_maps.vanilla1_16_5SourceMap
import java.io.File
import java.net.URLClassLoader
import java.security.ProtectionDomain
import javax.inject.Named
import javax.inject.Singleton

@Module(includes = [ServerModule::class])
abstract class CraftItModule {
    companion object {
        @Provides
        @Singleton
        @Named("server")
        fun classLoader(configuration: Configuration): ClassLoader =
            URLClassLoader(arrayOf(configuration.serverFile.toURI().toURL()))

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
        fun serverInitializer(vanillaServerInitializer: VanillaServerInitializer): ServerInitializer {
            return vanillaServerInitializer
        }

        @Provides
        @Named("pluginDirectory")
        fun pluginDirectory(@Named("serverDirectory") serverDirectory: File): File = serverDirectory.resolve("plugins")
    }


    @Binds
    @Singleton
    abstract fun server(to: PluggableServer): Server

    @Binds
    abstract fun commandParser(to: VanillaCommandParser): CommandParser

    @Binds
    abstract fun crackIt(to: CraftItApi): CraftIt
}
