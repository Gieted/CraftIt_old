package org.craftit.runtime.configuration

import dagger.Module
import dagger.Provides
import org.craftit.runtime.ServerModule
import java.io.File
import javax.inject.Named
import javax.inject.Singleton

@Module(includes = [ServerModule::class])
class ConfigurationModule {
    @Provides
    @Singleton
    @Named("configuration")
    fun configurationFile(@Named("serverDirectory") serverDirectory: File): File = serverDirectory.resolve("craftit.json")
}
