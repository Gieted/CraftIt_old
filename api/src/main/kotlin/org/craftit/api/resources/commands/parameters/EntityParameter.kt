package org.craftit.api.resources.commands.parameters

import com.mojang.brigadier.tree.ArgumentCommandNode

interface EntityParameter : Parameter {
    val multiple: Boolean
    val playerOnly: Boolean

    override fun <S> toBrigadierCommandNode(): ArgumentCommandNode<S, *>
}
