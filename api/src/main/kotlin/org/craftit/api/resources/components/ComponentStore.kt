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

    @Suppress("UNCHECKED_CAST")
    fun <V : T> require(type: KClass<V>): V = components.find { type == it::class }!! as V
    
    fun attach(component: T) {
        components.add(component)
        component.onAdded()
    }
    
    operator fun <V: T> contains(type: KClass<V>) = components.any { type == it::class }

    fun <V: T> removeAll(type: KClass<V>) {
        components.removeAll { type == it::class }
    }
}
