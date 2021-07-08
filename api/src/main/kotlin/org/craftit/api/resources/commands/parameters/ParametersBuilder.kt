package org.craftit.api.resources.commands.parameters


interface ParametersBuilder {
    interface ParameterRef

    fun int(
        name: String,
        optional: Boolean = false,
        min: Int = Int.MIN_VALUE,
        max: Int = Int.MAX_VALUE,
        children: ParametersBuilder.(ParameterRef) -> Unit
    )

    fun entity(
        name: String,
        optional: Boolean = false,
        multiple: Boolean,
        playerOnly: Boolean,
        children: ParametersBuilder.(ParameterRef) -> Unit
    )

    fun option(name: String, optional: Boolean = false, children: ParametersBuilder.(ParameterRef) -> Unit)

    operator fun List<Parameter>.invoke()

    operator fun Parameter.invoke()

    operator fun ParameterRef.invoke()
}
