package org.craftit.api.resources.commands

import org.craftit.api.resources.SingletonRegistry

interface CommandRegistry : SingletonRegistry<Command> {
    var root: Command
}
