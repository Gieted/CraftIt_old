package org.craftit.runtime.configuration

import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ConfigurationModule::class])
interface ConfigurationComponent {
    fun configurationRepository() : ConfigurationRepository
}
