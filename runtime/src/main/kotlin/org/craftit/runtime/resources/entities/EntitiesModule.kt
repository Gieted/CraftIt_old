package org.craftit.runtime.resources.entities

import dagger.Module
import org.craftit.runtime.resources.entities.player.PlayerModule

@Module(includes = [PlayerModule::class])
class EntitiesModule
