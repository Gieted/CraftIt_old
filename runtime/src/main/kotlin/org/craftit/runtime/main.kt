package org.craftit.runtime

import org.craftit.runtime.configuration.Configuration
import org.craftit.runtime.configuration.DaggerConfigurationComponent
import org.craftit.runtime.configuration.ConfigurationComponent

fun main() {
    fun loadConfiguration(): Configuration {
        val configurationComponent: ConfigurationComponent = DaggerConfigurationComponent.create()
        val configurationRepository = configurationComponent.configurationRepository()

        return configurationRepository.load()
    }

    val configuration = loadConfiguration()
    val component: MainComponent = DaggerMainComponent.factory().create(configuration)

    component.server().start()
}
