package org.craftit.api.resources.entities

interface HealthHolder {
    var health: Int

    fun heal(amount: Int) {
        if (amount > 0) {
            health += amount
        }
    }
}
