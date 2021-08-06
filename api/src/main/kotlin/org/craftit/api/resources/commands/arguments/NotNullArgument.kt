package org.craftit.api.resources.commands.arguments

import kotlin.reflect.KProperty

interface NotNullArgument<T : Any>: Argument<T> {
    override operator fun getValue(thisRef: Any?, prop: KProperty<*>): T
}
