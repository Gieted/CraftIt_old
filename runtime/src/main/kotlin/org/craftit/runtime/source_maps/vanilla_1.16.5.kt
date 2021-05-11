package org.craftit.runtime.source_maps

val vanilla1_16_5SourceMap = SourceMap.create {
    net.minecraft.network.play.ServerPlayNetHandler(
        "aay",
        player = "b",
        handleChat = "c",
        handleCommand = "d",
        handleCustomCommandSuggestions = "a"
    )

    net.minecraft.entity.player.ServerPlayerEntity(
        "aah",
        resetLastActionTime = "z",
        sendMessage = "a"
    )

    net.minecraft.entity.LivingEntity(
        "aqm",
        getHealth = "dk",
        setHealth = "c",
    )

    net.minecraft.server.management.PlayerList(
        "acu",
        broadcastMessage = "a",
    )

    net.minecraft.util.text.ChatType(
        "no",
        CHAT = "a",
        SYSTEM = "b",
        GAME_INFO = "c"
    )

    net.minecraft.util.text.StringTextComponent("oe")

    net.minecraft.util.text.Style(
        "ob",
        DEFAULT_FONT = "b"
    )

    net.minecraft.util.text.IFormattableTextComponent(
        "nx",
        append = "a",
        setStyle = "a",
    )

    net.minecraft.util.text.Color(
        "od",
        parseColor = "a"
    )

    net.minecraft.network.play.client.CTabCompletePacket("sh")

    net.minecraft.command.Commands(
        "dc",
        fillUsableCommands = "a",
    )

    net.minecraft.command.CommandSource(
        "db",
        source = "c",
        getPlayerOrException = "h",
        getEntity = "f"
    )

    net.minecraft.command.arguments.EntityArgument(
        "dk",
        entity = "a",
        entities = "b"
    )

    net.minecraft.server.dedicated.PropertyManager(
        "zk",
        loadFromFile = "a",
        LOGGER = "a"
    )
    
    net.minecraft.util.text.event.ClickEvent("np")
    
    net.minecraft.util.text.event.HoverEvent("nv")
    
    net.minecraft.util.text.ITextComponent("nr")
}
