package org.craftit.runtime.resources.commands

import com.mojang.brigadier.arguments.IntegerArgumentType
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.builder.RequiredArgumentBuilder
import com.mojang.brigadier.tree.ArgumentCommandNode
import com.mojang.brigadier.tree.CommandNode
import com.mojang.brigadier.tree.LiteralCommandNode
import org.craftit.api.resources.commands.parameters.EntityParameter
import org.craftit.api.resources.commands.parameters.NumericParameter
import org.craftit.api.resources.commands.parameters.OptionParameter
import org.craftit.api.resources.commands.parameters.Parameter
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
class ParameterConverter @Inject constructor(private val entityArgumentWrapper: EntityArgumentWrapper) {

    fun <T> Parameter.toBrigadierCommandNode(): CommandNode<T> = (when {
        this is NumericParameter<*> && this.type == Int::class -> (this as NumericParameter<Int>).toBrigadierCommandNode<Any>()
        this is EntityParameter -> this.toBrigadierCommandNode<Any, Any>()
        this is OptionParameter -> this.toBrigadierCommandNode<Any>()
        else -> throw AssertionError()
    } as CommandNode<T>)
    
    fun <T, Y> EntityParameter.toBrigadierCommandNode(): ArgumentCommandNode<T, Y> =
        RequiredArgumentBuilder.argument<T, Y>(
            this.name,
            if (this.multiple) entityArgumentWrapper.entities() else entityArgumentWrapper.entity()
        ).build().also { commandNode ->
            this.children.forEach { commandNode.addChild(it.toBrigadierCommandNode()) }
        }

    fun <T> NumericParameter<Int>.toBrigadierCommandNode(): ArgumentCommandNode<T, Int> =
        RequiredArgumentBuilder.argument<T, Int>(
            this.name, IntegerArgumentType.integer(this.min as Int, this.max as Int)
        ).build().also { commandNode ->
            this.children.forEach { commandNode.addChild(it.toBrigadierCommandNode()) }
        }

    fun <T> OptionParameter.toBrigadierCommandNode(): LiteralCommandNode<T> =
        LiteralArgumentBuilder.literal<T>(this.name).build().also { commandNode ->
            this.children.forEach { commandNode.addChild(it.toBrigadierCommandNode()) }
        }
}
