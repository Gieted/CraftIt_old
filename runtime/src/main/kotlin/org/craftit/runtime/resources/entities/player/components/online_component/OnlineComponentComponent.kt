package org.craftit.runtime.resources.entities.player.components.online_component

import dagger.BindsInstance
import dagger.Subcomponent
import org.craftit.api.resources.entities.player.Player
import org.craftit.api.resources.entities.player.connector.Connector
import org.craftit.api.resources.entities.player.input_resolver.InputResolver
import org.craftit.api.resources.entities.player.presenter.Presenter

@OnlineComponentScope
@Subcomponent(modules = [OnlineComponentModule::class])
interface OnlineComponentComponent {
    
    fun inputResolver(): InputResolver
    
    fun presenter(): Presenter

    fun connectorFactory(): Connector.Factory

    @Subcomponent.Factory
    interface Factory {
        fun create(@BindsInstance player: Player): OnlineComponentComponent
    }
}
