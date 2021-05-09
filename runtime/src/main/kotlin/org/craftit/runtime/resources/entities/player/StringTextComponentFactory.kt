package org.craftit.runtime.resources.entities.player

import org.craftit.runtime.source_maps.SourceMap
import java.lang.reflect.Constructor
import javax.inject.Inject
import javax.inject.Named

class StringTextComponentFactory @Inject constructor(sourceMap: SourceMap, @Named("server") classLoader: ClassLoader) {
    private val constructor: Constructor<*> by lazy {
        with(sourceMap { net.minecraft.util.text.StringTextComponent }) {
            val stringTextComponentClass = classLoader.loadClass(this())
            stringTextComponentClass.getConstructor(String::class.java)
        }
    }

    fun create(text: String): Any = constructor.newInstance(text)
}
