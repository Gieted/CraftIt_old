package org.craftit.api.resources.entities.player

interface InputResolver {
    fun onChat(message: String)
}
