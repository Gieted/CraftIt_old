package org.craftit.test_plugin.commands

import org.craftit.api.CraftIt
import org.craftit.api.resources.commands.Command
import org.craftit.api.resources.entities.HealthHolder

class Heal(override val id: String, craftIt: CraftIt) : Command by craftIt.command({
    val targets by entitiesArgument("targets", optional = issuer is HealthHolder)
    val amount by intArgument("amount", min = 1)

    execute {
        targets?.filter { it is HealthHolder }?.forEach { (it as HealthHolder).heal(amount) }
        
        if (targets == null) {
            (issuer as HealthHolder).heal(amount)
        }
    }
})
