package org.craftit.api.resources.entities.player

import java.util.*

interface OnlinePlayerRegistry {
    fun getByUUID(uuid: UUID): Player?
}
