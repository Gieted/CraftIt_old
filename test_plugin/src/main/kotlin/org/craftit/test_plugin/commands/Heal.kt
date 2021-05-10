package org.craftit.test_plugin.commands

import org.craftit.api.Color
import org.craftit.api.text_utils.invoke
import org.craftit.api.resources.commands.QuickCommand
import org.craftit.api.resources.entities.HealthHolder
import org.craftit.api.resources.entities.player.Player

class Heal(override val id: String) : QuickCommand() {
    override fun Command.define() {
        val targets by entitiesArgument(optional = issuer is HealthHolder)
        val amount by intArgument(min = 1)

        execute {
            targets?.filter { it is HealthHolder }?.forEach { (it as HealthHolder).heal(amount) }
            if (targets == null) {
                (issuer as Player).heal(amount)
            }
            issuer.sendMessage("Healed!"(Color.Aqua, bold = true))
        }
    }
}
