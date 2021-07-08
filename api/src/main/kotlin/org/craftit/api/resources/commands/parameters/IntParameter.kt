package org.craftit.api.resources.commands.parameters

import com.mojang.brigadier.tree.ArgumentCommandNode

interface IntParameter : Parameter {
    val min: Int
    val max: Int

    override fun <T> toBrigadierCommandNode(): ArgumentCommandNode<T, Int>
} 
