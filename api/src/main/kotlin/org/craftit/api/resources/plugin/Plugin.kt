package org.craftit.api.resources.plugin

import org.craftit.api.resources.Resource

interface Plugin: Resource {
    fun enable()
}
