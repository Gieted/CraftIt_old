package org.craftit.runtime.resources.plugins.api.builders

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.mockk.mockk
import org.craftit.api.resources.commands.parameters.EntityParameter
import org.craftit.api.resources.commands.parameters.IntParameter
import org.craftit.api.resources.commands.parameters.OptionParameter
import org.craftit.runtime.resources.commands.ParameterConverter
import io.kotest.matchers.types.shouldBeInstanceOf

class ParametersBuilderImplTest : FunSpec({
    val parameterConverter = mockk<ParameterConverter>()
    val builder = ParametersBuilderImpl(parameterConverter)

    test("returns empty list, when no parameters provided") {
        builder.build() shouldBe emptyList()
    }

    test("works with a single int parameter") {
        builder.apply {
            int("integer", min = -10, max = 5)
        }.build().single().also {
            it.shouldBeInstanceOf<IntParameter>()
            it.name shouldBe "integer"
            it.children shouldBe emptyList()
            it.optional shouldBe false
            it.min shouldBe -10
            it.max shouldBe 5
        }
    }

    test("works with a single entity parameter") {
        builder.apply {
            entity("entity", multiple = true, playerOnly = false)
        }.build().single().also {
            it.shouldBeInstanceOf<EntityParameter>()
            it.name shouldBe "entity"
            it.children shouldBe emptyList()
            it.optional shouldBe false
            it.multiple shouldBe true
            it.playerOnly shouldBe false
        }
    }

    test("works with a single option parameter") {
        builder.apply {
            option("option")
        }.build().single().also {
            it.shouldBeInstanceOf<OptionParameter>()
            it.name shouldBe "option"
            it.children shouldBe emptyList()
            it.optional shouldBe false
        }
    }

    test("works with multiple top-level parameters") {
        val parameters = builder.apply {
            int("1")
            option("2")
            entity("3", multiple = false, playerOnly = false)
        }.build()

        parameters.size shouldBe 3
        parameters[0].shouldBeInstanceOf<IntParameter>()
        parameters[1].shouldBeInstanceOf<OptionParameter>()
        parameters[2].shouldBeInstanceOf<EntityParameter>()
    }

    test("works with parameter refs") {
        builder.apply {
            int("x") {
                it()
            }
        }.build().single().also {
            it.children.single() shouldBe it
        }
    }

    test("works with multiple parameter refs") {
        builder.apply {
            int("x") { x ->
                int("y") { y ->
                    x()
                    y()
                }
            }
        }.build().single().also { x ->
            x.children.single().also { y ->
                y.children.size shouldBe 2
                y.children[0] shouldBe x
                y.children[1] shouldBe y
            }
        }
    }

    test("should work with root ref") {
        builder.apply {
            int("1") {
                root()
            }
            int("2")
        }.build().first().also {
            it.children.size shouldBe 2
            it.children[0] shouldBe it
            it.children[1].name shouldBe "2"
        }
    }

    test("should work with children ref") {
        builder.apply {
            val one = int("1") {
                int("1.1")
                int("1.2")
            }
            int("2") {
                one.children()
            }
        }.build()[1].also {
            it.children.size shouldBe 2
            it.children[0].name shouldBe "1.1"
            it.children[1].name shouldBe "1.2"
        }
    }

    test("should work with both normal and root/children refs") {
        builder.apply {
            val one = int("1") {
                int("1.1")
            }
            int("2") {
                one.children()
                int("2.1")
            }
        }.build()[1].also {
            it.children.size shouldBe 2
            it.children[0].name shouldBe "1.1"
            it.children[1].name shouldBe "2.1"
        }
    }

    test("should work with already built parameters") {
        val one = ParametersBuilderImpl(parameterConverter).apply {
            int("1")
        }.build().single()

        val parameters = ParametersBuilderImpl(parameterConverter).apply {
            one()
            int("2")
        }.build()

        builder.apply {
            int("top") {
                parameters()
            }
        }.build().single().also {
            it.children.size shouldBe 2
            it.children[0].name shouldBe "1"
            it.children[1].name shouldBe "2"
        }
    }
})
