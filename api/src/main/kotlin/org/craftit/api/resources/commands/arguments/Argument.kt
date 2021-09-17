package org.craftit.api.resources.commands.arguments

import org.craftit.api.resources.commands.parsing.ArgumentParser
import kotlin.reflect.KProperty

interface Argument<T : Any> {
    operator fun getValue(thisRef: Any?, prop: KProperty<*>): T?

    val parser: ArgumentParser<T>
}
