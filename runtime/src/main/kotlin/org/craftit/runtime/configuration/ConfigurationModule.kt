package org.craftit.runtime.configuration

import dagger.Module
import dagger.Provides
import java.io.File
import javax.inject.Named
import javax.inject.Singleton

@Module
class ConfigurationModule {
    @Provides
    @Singleton
    @Named("serverDirectory")
    fun serverDirectory(): File = File("")
    
    @Provides
    @Singleton
    @Named("configuration")
    fun configurationFile(@Named("serverDirectory") serverDirectory: File): File = serverDirectory.resolve("craftit.json")
}
