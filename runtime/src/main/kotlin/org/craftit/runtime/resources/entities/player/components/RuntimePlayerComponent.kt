package org.craftit.runtime.resources.entities.player.components

import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import org.craftit.api.resources.entities.player.components.PlayerComponent
import org.craftit.runtime.resources.entities.player.NativeConnectorImpl

class RuntimePlayerComponent @AssistedInject constructor(
    @Assisted val nativeConnector: NativeConnectorImpl
) : PlayerComponent {

    @AssistedFactory
    interface Factory {
        fun create(nativeConnector: NativeConnectorImpl): RuntimePlayerComponent
    }

    override fun onAdded() {
    }
}
