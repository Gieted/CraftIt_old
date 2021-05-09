package org.craftit.api.resources

interface Registry<T : Resource>: Collection<T> {
    operator fun get(id: String): T?

    operator fun set(id: String, value: T)
}
