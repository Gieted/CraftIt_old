package org.craftit.runtime

import dagger.BindsInstance
import dagger.Component
import org.craftit.runtime.configuration.Configuration
import org.craftit.runtime.server_initializer.ServerInitializer
import javax.inject.Singleton

@Singleton
@Component(modules = [CraftItModule::class])
interface CraftItComponent {
    fun serverInitializer(): ServerInitializer

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance configuration: Configuration): CraftItComponent
    }
}
