package org.craftit.runtime.resources.plugin

import com.google.gson.Gson
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import org.craftit.api.CraftIt
import org.craftit.api.Server
import org.craftit.api.resources.plugin.Plugin
import java.io.File
import java.net.URLClassLoader
import java.util.zip.ZipFile

class FilePlugin @AssistedInject constructor(
    @Assisted private val file: File,
    @Assisted private val server: Server,
    private val pluginApiFactory: PluginApi.Factory
) : Plugin {

    @AssistedFactory
    interface Factory {
        fun create(file: File, server: Server): FilePlugin
    }

    private val gson = Gson()
    private var plugin: Plugin? = null

    private fun loadManifest(): PluginManifest {
        val zipFile = ZipFile(file)
        val manifestJson = zipFile.getInputStream(zipFile.getEntry("plugin-manifest.json")).bufferedReader().readText()

        return gson.fromJson(manifestJson, PluginManifest::class.java)
    }

    override fun enable() {
        val manifest = loadManifest()
        val classLoader = URLClassLoader(arrayOf(file.toURI().toURL()))
        val pluginClass = classLoader.loadClass(manifest.pluginClass)
        val pluginApi = pluginApiFactory.create(server, manifest.id)
        plugin = pluginClass.getConstructor(CraftIt::class.java).newInstance(pluginApi) as Plugin
        plugin!!.enable()
    }

    override val id: String
        get() = plugin?.id ?: loadManifest().id
}
