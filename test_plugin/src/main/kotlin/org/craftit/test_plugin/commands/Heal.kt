package org.craftit.test_plugin.commands

import org.craftit.api.resources.commands.QuickCommand
import org.craftit.api.resources.entities.HealthHolder

class Heal(override val id: String) : QuickCommand() {
    override fun Command.define() {
        val targets by entitiesArgument(optional = issuer is HealthHolder)
        val amount by intArgument(min = 1)

        execute {
            targets?.filter { it is HealthHolder }?.forEach { (it as HealthHolder).heal(amount) }
            if (targets == null) {
                (issuer as HealthHolder).heal(amount)
            }
        }
    }
}
