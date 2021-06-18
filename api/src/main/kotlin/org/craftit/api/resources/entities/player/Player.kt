package org.craftit.api.resources.entities.player

import org.craftit.api.Server
import org.craftit.api.resources.entities.Entity
import org.craftit.api.resources.entities.HealthHolder

interface Player : Entity, HealthHolder {
    val nativePlayer: NativePlayer
    val inputResolver: InputResolver
    val controller: PlayerController
    val connector: Connector
    val server: Server
    val presenter: PlayerPresenter
}
