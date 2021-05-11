package org.craftit.api.resources.entities.player

import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class VanillaInputResolver @AssistedInject constructor(@Assisted private val player: Player) : InputResolver {
    @AssistedFactory
    interface Factory {
        fun create(player: Player): VanillaInputResolver
    }

    override fun onChat(message: String) {
        fun String.isCommand() = startsWith("/")

        if (message.isCommand()) {
            player.server.commands.root.execute(player, message.drop(1))
        }
    }
}
