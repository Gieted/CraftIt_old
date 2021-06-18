package org.craftit.runtime.resources.entities.player

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NativeConnectorCache @Inject constructor(
    private val nativeConnectorFactory: NativeConnectorImpl.Factory
) {
    private val connectors = mutableMapOf<Any, NativeConnectorImpl>()

    fun get(serverPlayerEntity: Any) = connectors.getOrPut(serverPlayerEntity) {
        nativeConnectorFactory.create(serverPlayerEntity)
    }
}
