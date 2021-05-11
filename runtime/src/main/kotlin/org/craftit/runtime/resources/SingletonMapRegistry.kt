package org.craftit.runtime.resources

import org.craftit.api.resources.Resource
import org.craftit.api.resources.SingletonRegistry

open class SingletonMapRegistry<T: Resource>(vararg elements: Pair<String, T>): MapRegistry<T>(*elements), SingletonRegistry<T>
