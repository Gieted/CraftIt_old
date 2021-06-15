package org.craftit.runtime.resources

import dagger.Module
import org.craftit.runtime.resources.entities.EntitiesModule

@Module(includes = [EntitiesModule::class])
class ResourcesModule
