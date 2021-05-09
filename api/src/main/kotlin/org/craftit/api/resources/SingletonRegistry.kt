package org.craftit.api.resources

interface SingletonRegistry<T : Resource> : Registry<T> {
    fun getById(id: String) = find { it.id == id }
}
