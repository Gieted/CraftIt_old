package org.craftit.api.resources.commands.parameters


interface ParametersBuilder {
    interface ParameterRef {
        val children: List<ParameterRef>
    }

    fun int(
        name: String,
        optional: Boolean = false,
        min: Int = Int.MIN_VALUE,
        max: Int = Int.MAX_VALUE,
        children: ParametersBuilder.(ParameterRef) -> Unit
    ): ParameterRef

    fun entity(
        name: String,
        optional: Boolean = false,
        multiple: Boolean,
        playerOnly: Boolean,
        children: ParametersBuilder.(ParameterRef) -> Unit
    ): ParameterRef

    fun option(name: String, optional: Boolean = false, children: ParametersBuilder.(ParameterRef) -> Unit): ParameterRef

    operator fun List<Parameter>.invoke()
    
    operator fun Parameter.invoke()

    operator fun ParameterRef.invoke()
    
    fun ParameterRef.children()
    
    fun root()
}
