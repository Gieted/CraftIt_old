package org.craftit.runtime.resources

import org.craftit.api.resources.Resource

open class SingletonMapRegistry<T: Resource>(vararg elements: Pair<String, T>): MapRegistry<T>(*elements),
    SingletonRegistry<T>
