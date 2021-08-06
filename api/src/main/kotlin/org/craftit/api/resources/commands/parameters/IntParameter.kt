package org.craftit.api.resources.commands.parameters

import com.mojang.brigadier.tree.ArgumentCommandNode

interface IntParameter : Parameter {
    val min: Int
    val max: Int

    override fun <S> toBrigadierCommandNode(): ArgumentCommandNode<S, Int>
} 
