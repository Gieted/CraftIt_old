package org.craftit.runtime.bytecode_modifiers

import javax.inject.Inject

class MainBytecodeModifier @Inject constructor(
    private val craftItItemRegistry: CraftItItemRegistry,
    private val registryModifier: RegistryModifier,
    private val propertyManagerModifier: PropertyManagerModifier
) {

    fun modify() {
        val modifiers = listOf(
            craftItItemRegistry,
            registryModifier,
            propertyManagerModifier
        )
        
        modifiers.forEach { 
            it.configure()
        }
    }
}
