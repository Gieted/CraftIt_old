package org.craftit.runtime.server

import dagger.Binds
import dagger.Module
import dagger.Provides
import org.craftit.api.resources.entities.EntityRegistry
import org.craftit.runtime.configuration.Configuration
import org.craftit.runtime.resources.entities.VanillaEntityRegistry
import org.craftit.runtime.server.initializers.NativeServerInitializer
import org.craftit.runtime.server.initializers.VanillaServerInitializer
import java.net.URLClassLoader

@Module
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
    abstract fun nativeServerInitializer(to: VanillaServerInitializer): NativeServerInitializer
}
