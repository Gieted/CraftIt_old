package org.craftit.runtime.configuration

import com.google.gson.GsonBuilder
import java.io.File
import javax.inject.Inject
import javax.inject.Named

class ConfigurationRepository @Inject constructor(
    @Named("configuration") private val configurationFile: File,
    fileAdapter: FileAdapter
) {
    private val gson = GsonBuilder().registerTypeAdapter(File::class.java, fileAdapter).create()


    fun load(): Configuration {
        val configurationJson = configurationFile.readText()

        return gson.fromJson(configurationJson, Configuration::class.java)
    }
}
