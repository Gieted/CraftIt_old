package org.craftit.runtime.resources.plugins.api.builders

import io.kotest.core.spec.style.FunSpec
import io.mockk.mockk
import org.craftit.api.resources.commands.CommandIssuer
import javax.inject.Provider

class CommandBuilderImplTest : FunSpec({
    val issuer = mockk<CommandIssuer>()
    val parameterBuilder = mockk<Provider<ParametersBuilderImpl>>()
    val factory = object : CommandBuilderImpl.Factory {
        override fun create(issuer: CommandIssuer): CommandBuilderImpl =
            CommandBuilderImpl(issuer, this, parameterBuilder)
    }
    val builder = factory.create(issuer)

    test("builds a 0 argument command") {
        builder.apply { 
            execute {
            }
        }
    }
})
