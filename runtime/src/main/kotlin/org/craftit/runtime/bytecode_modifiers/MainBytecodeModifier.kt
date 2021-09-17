package org.craftit.runtime.bytecode_modifiers

import javax.inject.Inject

class MainBytecodeModifier @Inject constructor(private val propertyManagerModifier: PropertyManagerModifier) {

    fun modify() {
        propertyManagerModifier.modify()
    }
}
