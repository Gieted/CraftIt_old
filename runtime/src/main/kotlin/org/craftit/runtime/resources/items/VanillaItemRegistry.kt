package org.craftit.runtime.resources.items

import org.craftit.api.resources.items.Item
import org.craftit.api.resources.items.ItemRegistry
import org.craftit.runtime.resources.AbstractRegistry
import org.craftit.runtime.server.ServerScope
import javax.inject.Inject

@ServerScope
class VanillaItemRegistry @Inject constructor(): AbstractRegistry<Item>(), ItemRegistry {
}
