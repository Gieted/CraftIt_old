package org.craftit.runtime.resources

import org.craftit.api.resources.Registry
import org.craftit.api.resources.Resource

abstract class AbstractRegistry<T : Resource>: Registry<T> {
    protected val list = mutableListOf<T>()
    
    override val size: Int
        get() = list.size

    override fun contains(element: T): Boolean = list.contains(element)

    override fun containsAll(elements: Collection<T>): Boolean = list.containsAll(elements)

    override fun isEmpty(): Boolean = list.isEmpty()

    override fun iterator(): Iterator<T> = list.iterator()
}
