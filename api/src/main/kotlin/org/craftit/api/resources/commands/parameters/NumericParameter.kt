package org.craftit.api.resources.commands.parameters

import kotlin.reflect.KClass

data class NumericParameter<T : Comparable<T>>(
    override val name: String,
    val min: T?,
    val max: T?,
    val type: KClass<T>
) : Parameter
