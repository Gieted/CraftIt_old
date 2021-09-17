package org.craftit.runtime.text

import org.craftit.api.text.Text
import org.craftit.runtime.server.ServerScope
import org.craftit.runtime.source_maps.SourceMap
import java.lang.reflect.Constructor
import java.lang.reflect.Method
import javax.inject.Inject

@ServerScope
class NativeStringTextComponentConverter @Inject constructor(
    sourceMap: SourceMap,
    classLoader: ClassLoader,
    private val nativeStyleFactory: NativeStyleFactory
) {
    private val constructor: Constructor<*> by lazy {
        with(sourceMap { net.minecraft.util.text.StringTextComponent }) {
            val stringTextComponentClass = classLoader.loadClass(this())
            stringTextComponentClass.getConstructor(String::class.java)
        }
    }

    private val setStyleMethod: Method by lazy {
        val styleClass = classLoader.loadClass(sourceMap { net.minecraft.util.text.Style }())
        with(sourceMap { net.minecraft.util.text.IFormattableTextComponent }) {
            val iFormattableTextComponent = classLoader.loadClass(this())
            iFormattableTextComponent.getDeclaredMethod(setStyle, styleClass)
        }
    }

    private val appendMethod: Method by lazy {
        val iTextComponentClass = classLoader.loadClass(sourceMap { net.minecraft.util.text.ITextComponent }())
        with(sourceMap { net.minecraft.util.text.IFormattableTextComponent }) {
            val iFormattableTextComponent = classLoader.loadClass(this())
            iFormattableTextComponent.getDeclaredMethod(append, iTextComponentClass)
        }
    }

    fun convert(text: Text): Any {
        var instance = constructor.newInstance("")

        for (fragment in text.fragments) {
            val spanInstance = constructor.newInstance(fragment.content)
            setStyleMethod.invoke(spanInstance, nativeStyleFactory.create(fragment.properties))
            instance = appendMethod.invoke(instance, spanInstance)
        }

        return instance
    }
}
