package org.craftit.api.resources.plugins

import org.craftit.api.resources.Resource

interface Plugin: Resource {
    fun enable()
}
