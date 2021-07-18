package org.craftit.runtime.resources.plugins.api

import dagger.BindsInstance
import dagger.Subcomponent
import org.craftit.api.CraftIt
import org.craftit.runtime.resources.plugins.api.builders.PluginBuilderImpl
import javax.inject.Provider

@PluginScope
@Subcomponent
interface PluginComponent {
    
    fun pluginBuilderProvider(): Provider<PluginBuilderImpl>

    @Subcomponent.Factory
    interface Factory {
        fun create(@BindsInstance craftIt: CraftIt): PluginComponent
    }
}
