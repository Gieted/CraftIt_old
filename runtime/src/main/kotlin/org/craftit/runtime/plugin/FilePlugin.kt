package org.craftit.runtime.plugin

import com.google.gson.Gson
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import org.craftit.api.CraftIt
import org.craftit.api.Plugin
import org.craftit.runtime.CraftItApi
import org.craftit.runtime.plugin.manifest.PluginManifest
import java.io.File
import java.net.URLClassLoader
import java.util.zip.ZipFile

class FilePlugin @AssistedInject constructor(@Assisted private val file: File, private val craftIt: CraftItApi) : Plugin {
    @AssistedFactory
    interface Factory {
        fun create(file: File): FilePlugin
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
        plugin = pluginClass.getConstructor(CraftIt::class.java).newInstance(craftIt) as Plugin
        plugin!!.enable()
    }
}
