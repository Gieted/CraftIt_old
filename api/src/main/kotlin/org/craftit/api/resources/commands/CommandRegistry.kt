package org.craftit.api.resources.commands

import org.craftit.api.resources.SingletonRegistry

interface CommandRegistry : SingletonRegistry<Command> {
    val root: Command
}
