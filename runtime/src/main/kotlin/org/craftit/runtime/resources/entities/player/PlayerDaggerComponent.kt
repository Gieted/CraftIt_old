package org.craftit.runtime.resources.entities.player

import dagger.BindsInstance
import dagger.Subcomponent
import org.craftit.api.resources.entities.player.Player
import org.craftit.api.resources.entities.player.controller.PlayerController

@PlayerScope
@Subcomponent(modules = [PlayerModule::class])
interface PlayerDaggerComponent {
    
    fun controller(): PlayerController
    
    @Subcomponent.Factory
    interface Factory {
        fun create(@BindsInstance player: Player): PlayerDaggerComponent
    }
}
