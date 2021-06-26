package org.craftit.api.resources.components

import kotlin.reflect.KClass

class ComponentStore<T: Component> : Collection<T> {
    private val components = mutableListOf<T>()
    
    override val size: Int
        get() = components.size

    override fun contains(element: T): Boolean = components.contains(element)

    override fun containsAll(elements: Collection<T>): Boolean = components.containsAll(elements)

    override fun isEmpty(): Boolean = components.isEmpty()

    override fun iterator(): Iterator<T> = components.iterator()

    @Suppress("UNCHECKED_CAST")
    operator fun <V : T> get(type: KClass<V>): V? = components.find { type == it::class } as V?
    
    fun add(component: T) {
        components.add(component)
        component.onAdded()
    }
}
