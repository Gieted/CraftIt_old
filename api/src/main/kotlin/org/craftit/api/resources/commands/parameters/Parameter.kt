package org.craftit.api.resources.commands.parameters

interface Parameter {
    val name: String
    val children: List<Parameter>
    val optional: Boolean
}
