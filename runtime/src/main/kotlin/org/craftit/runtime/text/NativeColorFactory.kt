package org.craftit.runtime.text

import org.craftit.runtime.source_maps.SourceMap
import javax.inject.Inject
import javax.inject.Named

class NativeColorFactory @Inject constructor(
    sourceMap: SourceMap,
    @Named("server") classLoader: ClassLoader) {
    
    private val parseColor by lazy {
        with(sourceMap { net.minecraft.util.text.Color }) {
            val colorClass = classLoader.loadClass(this())
            colorClass.getDeclaredMethod(parseColor, String::class.java)
        }
    }
    
    fun create(hexCode: String): Any {
        return parseColor.invoke(null, hexCode)
    }
}
