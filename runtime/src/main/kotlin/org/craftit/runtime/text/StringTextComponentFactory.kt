package org.craftit.runtime.text

import org.craftit.api.Text
import org.craftit.runtime.source_maps.SourceMap
import java.lang.reflect.Constructor
import java.lang.reflect.Method
import javax.inject.Inject
import javax.inject.Named

class StringTextComponentFactory @Inject constructor(
    sourceMap: SourceMap,
    @Named("server") classLoader: ClassLoader,
    private val styleFactory: StyleFactory
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

    fun create(text: Text): Any {
        var instance = constructor.newInstance("")

        for (fragment in text.fragments) {
            val spanInstance = constructor.newInstance(fragment.content)
            setStyleMethod.invoke(spanInstance, styleFactory.create(fragment.properties))
            instance = appendMethod.invoke(instance, spanInstance)
        }

        return instance
    }
}
