package org.craftit.runtime.resources

import org.craftit.api.resources.IdGenerator
import java.util.*
import javax.inject.Inject

class VanillaIdGenerator @Inject constructor() : IdGenerator {
    override fun generate(): String = UUID.randomUUID().toString()
}
