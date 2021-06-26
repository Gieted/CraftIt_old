package org.craftit.runtime.resources

import org.craftit.api.resources.Resource

open class MapRegistry<T: Resource>(vararg elements: Pair<String, T>) : Registry<T> {
    private val map = mutableMapOf(*elements)

    override val size: Int = map.size

    override fun contains(element: T): Boolean = map.values.contains(element)

    override fun containsAll(elements: Collection<T>): Boolean = map.values.containsAll(elements)

    override fun isEmpty(): Boolean = map.isEmpty()

    override fun iterator(): Iterator<T> = map.values.iterator()

    override operator fun get(id: String): T? = map[id]

    override fun set(id: String, value: T) {
        map[id] = value
    }
}
