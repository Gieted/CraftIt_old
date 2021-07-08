package org.craftit.api.resources.commands.parameters

import com.mojang.brigadier.tree.CommandNode

interface Parameter {
    val name: String
    val optional: Boolean
    val children: List<Parameter>

    fun <T> toBrigadierCommandNode(): CommandNode<T>
}
