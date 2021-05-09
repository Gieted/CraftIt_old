package org.craftit.runtime

import dagger.Module
import dagger.Provides
import java.io.File
import javax.inject.Named
import javax.inject.Singleton

@Module
class ServerModule {
    @Provides
    @Singleton
    @Named("serverDirectory")
    fun serverDirectory(): File = File("")
}
