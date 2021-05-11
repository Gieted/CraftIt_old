package org.craftit.runtime.resources.commands

import org.craftit.api.resources.commands.Command
import org.craftit.api.resources.commands.CommandRegistry
import org.craftit.runtime.resources.SingletonMapRegistry
import javax.inject.Inject
import javax.inject.Named

class CommandMapRegistry @Inject constructor(@Named("root") override var root: Command) : SingletonMapRegistry<Command>(), CommandRegistry
