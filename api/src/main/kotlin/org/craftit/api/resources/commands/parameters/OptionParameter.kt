package org.craftit.api.resources.commands.parameters

import com.mojang.brigadier.tree.LiteralCommandNode

interface OptionParameter: Parameter {
    override fun <S> toBrigadierCommandNode(): LiteralCommandNode<S>
}
