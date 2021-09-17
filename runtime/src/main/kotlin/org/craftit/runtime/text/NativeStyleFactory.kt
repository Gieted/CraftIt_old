package org.craftit.runtime.text

import org.craftit.api.text.Text
import org.craftit.runtime.server.ServerScope
import org.craftit.runtime.source_maps.SourceMap
import javax.inject.Inject

@ServerScope
class NativeStyleFactory @Inject constructor(
    sourceMap: SourceMap,
    classLoader: ClassLoader,
    private val nativeColorFactory: NativeColorFactory
) {
    private val constructor by lazy {
        with(sourceMap { net.minecraft.util.text.Style }) {
            val style = classLoader.loadClass(this())
            val constructor = style.declaredConstructors.find { it.parameterCount == 10 }!!
            constructor.isAccessible = true

            constructor
        }
    }

    private val defaultFont by lazy {
        with(sourceMap { net.minecraft.util.text.Style }) {
            val style = classLoader.loadClass(this())
            style.getDeclaredField(DEFAULT_FONT).get(null)
        }
    }
    
    fun create(properties: Text.Properties): Any {
        val color = nativeColorFactory.create(properties.color.hexCode)
        val bold = properties.bold
        val italic = properties.italic
        val underlined = properties.underlined
        val strikethrough = properties.strikethrough
        val obfuscated = properties.obfuscated
        val insertion = properties.insertion
        val font = defaultFont

        return constructor.newInstance(
            color,
            bold,
            italic,
            underlined,
            strikethrough,
            obfuscated,
            null as Any?,
            null as Any?,
            insertion,
            font,
        )
    }
}
