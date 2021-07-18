package org.craftit.runtime.resources

import dagger.Binds
import dagger.Module
import org.craftit.api.resources.IdGenerator
import org.craftit.api.resources.plugins.PluginRegistry
import org.craftit.runtime.resources.entities.EntitiesModule
import org.craftit.runtime.resources.plugins.VanillaPluginRegistry

@Module(includes = [EntitiesModule::class])
abstract class ResourcesModule {

    @Binds
    abstract fun pluginRegistry(to: VanillaPluginRegistry): PluginRegistry
    
    @Binds
    abstract fun idGenerator(to: VanillaIdGenerator): IdGenerator
}
