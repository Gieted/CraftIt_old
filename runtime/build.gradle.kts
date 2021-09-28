plugins {
    kotlin("jvm")
    kotlin("kapt")
    id("idea")
}

group = "org.craftit.runtime"
version = "0.1-SNAPSHOT"

repositories {
    maven(url = "https://libraries.minecraft.net")
    google()
}

dependencies {
    implementation(project(":api"))
    implementation(project(":test_plugin"))
    implementation("org.javassist:javassist:3.28.0-GA")

    implementation("com.google.dagger:dagger:2.38.1")
    kapt("com.google.dagger:dagger-compiler:2.38.1")

    implementation("com.google.code.gson:gson:2.8.8")

    implementation("com.mojang:brigadier:1.0.18")
}

tasks.getByPath("compileKotlin").apply {
    dependsOn("generateSourceMap")
}

tasks.register<GenerateSourceMap>("generateSourceMap") {
    packageName.set("org.craftit.runtime.source_maps")

    scheme.set(
        mapOf(
            "net.minecraft.network.play.ServerPlayNetHandler" to listOf(
                "player",
                "handleChat",
                "handleCommand",
                "handleCustomCommandSuggestions",
                "send"
            ),

            "net.minecraft.entity.Entity" to listOf(
                "getUUID"
            ),

            "net.minecraft.entity.player.ServerPlayerEntity" to listOf(
                "resetLastActionTime",
                "sendMessage",
                "connection",
                "getUUID",
                "disconnect"
            ),

            "net.minecraft.entity.LivingEntity" to listOf(
                "getHealth",
                "setHealth"
            ),

            "net.minecraft.server.management.PlayerList" to listOf(
                "broadcastMessage",
                "placeNewPlayer",
                "respawn"
            ),

            "net.minecraft.util.text.ChatType" to listOf(
                "CHAT",
                "SYSTEM",
                "GAME_INFO"
            ),

            "net.minecraft.util.text.StringTextComponent" to emptyList(),

            "net.minecraft.util.text.Style" to listOf(
                "DEFAULT_FONT"
            ),

            "net.minecraft.util.text.IFormattableTextComponent" to listOf(
                "append",
                "setStyle"
            ),

            "net.minecraft.util.text.Color" to listOf(
                "parseColor",
            ),

            "net.minecraft.network.play.client.CTabCompletePacket" to emptyList(),

            "net.minecraft.command.Commands" to listOf(
                "fillUsableCommands"
            ),

            "net.minecraft.command.CommandSource" to listOf(
                "source",
                "getPlayerOrException",
                "getEntity"
            ),

            "net.minecraft.command.arguments.EntityArgument" to listOf(
                "entity",
                "entities"
            ),

            "net.minecraft.server.dedicated.PropertyManager" to listOf(
                "loadFromFile",
                "LOGGER"
            ),

            "net.minecraft.util.text.event.ClickEvent" to listOf(),

            "net.minecraft.util.text.event.HoverEvent" to listOf(),

            "net.minecraft.util.text.ITextComponent" to listOf(),

            "net.minecraft.network.play.client.CChatMessagePacket" to listOf(
                "getMessage"
            ),

            "net.minecraft.network.IPacket" to listOf(),

            "net.minecraft.network.play.server.SChatPacket" to listOf(),

            "net.minecraft.network.NetworkManager" to listOf(),

            "net.minecraft.util.registry.DefaultedRegistry" to listOf(
                "registerMapping",
                "register"
            ),

            "net.minecraft.util.registry.Registry" to listOf(
                "ITEM",
                "ITEM_REGISTRY",
                "registerDefaulted",
                "internalRegister"
            ),

            "net.minecraft.util.registry.MutableRegistry" to listOf("register"),

            "net.minecraft.util.RegistryKey" to listOf()
        )
    )
}
