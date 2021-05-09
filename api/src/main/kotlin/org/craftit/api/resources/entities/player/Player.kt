package org.craftit.api.resources.entities.player

import org.craftit.api.ChatParticipant
import org.craftit.api.Server
import org.craftit.api.resources.commands.CommandIssuer
import org.craftit.api.resources.entities.Entity
import org.craftit.api.resources.entities.HealthHolder

interface Player :
    Entity,
    CommandIssuer,
    ChatParticipant,
    HealthHolder {

    val nativePlayer: NativePlayer
    val inputResolver: InputResolver
    val controller: PlayerController
    val server: Server
}
