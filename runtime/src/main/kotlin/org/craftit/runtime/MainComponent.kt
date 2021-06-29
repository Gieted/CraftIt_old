package org.craftit.runtime

import dagger.BindsInstance
import dagger.Component
import org.craftit.api.Server
import org.craftit.runtime.configuration.Configuration
import javax.inject.Named
import javax.inject.Singleton

@Singleton
@Component(modules = [MainModule::class])
interface MainComponent {

    @Named("new")
    fun server(): Server

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance configuration: Configuration): MainComponent
    }
}
