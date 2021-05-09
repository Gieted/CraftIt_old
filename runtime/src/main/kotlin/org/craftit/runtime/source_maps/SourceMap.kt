@file:Suppress("PropertyName", "FunctionName", "unused", "UNUSED_EXPRESSION")

package org.craftit.runtime.source_maps

data class SourceMap(val net: Net) {
    abstract class Class(private val identifier: String) {
        operator fun invoke() = identifier
    }

    data class Net(val minecraft: Minecraft) {
        data class Minecraft(
            val network: Network,
            val entity: Entity,
            val server: Server,
            val util: Util,
            val command: Command
        ) {
            data class Network(val play: Play) {
                data class Play(val ServerPlayNetHandler: ServerPlayNetHandlerClass, val client: Client) {
                    class ServerPlayNetHandlerClass(
                        identifier: String,
                        val handleChat: String,
                        val handleCommand: String,
                        val player: String,
                        val handleCustomCommandSuggestions: String,
                    ) : Class(identifier)

                    data class Client(val CTabCompletePacket: CTabCompletePacketClass) {
                        class CTabCompletePacketClass(identifier: String) : Class(identifier)
                    }
                }
            }

            data class Entity(val player: Player, val LivingEntity: LivingEntityClass) {
                data class Player(val ServerPlayerEntity: ServerPlayerEntityClass) {
                    class ServerPlayerEntityClass(
                        identifier: String,
                        val resetLastActionTime: String,
                        val sendMessage: String
                    ) : Class(identifier)
                }

                class LivingEntityClass(identifier: String, val getHealth: String, val setHealth: String) :
                    Class(identifier)
            }

            data class Server(val management: Management) {
                data class Management(val PlayerList: PlayerListClass) {
                    class PlayerListClass(identifier: String, val broadcastMessage: String) : Class(identifier)
                }
            }

            data class Util(val text: Text) {
                data class Text(val ChatType: ChatTypeClass, val StringTextComponent: StringTextComponentClass) {
                    class ChatTypeClass(
                        identifier: String,
                        val CHAT: String,
                        val SYSTEM: String,
                        val GAME_INFO: String
                    ) : Class(identifier)

                    class StringTextComponentClass(identifier: String) : Class(identifier)
                }
            }

            data class Command(
                val Commands: CommandsClass,
                val CommandSource: CommandSourceClass,
                val arguments: Arguments
            ) {
                class CommandsClass(identifier: String, val fillUsableCommands: String) : Class(identifier)

                class CommandSourceClass(
                    identifier: String,
                    val source: String,
                    val getPlayerOrException: String,
                    val getEntity: String
                ) : Class(identifier)

                data class Arguments(val EntityArgument: EntityArgumentClass) {
                    class EntityArgumentClass(
                        identifier: String, val entity: String,
                        val entities: String
                    ) : Class(identifier)
                }
            }
        }
    }

    operator fun <T : Class> invoke(browse: SourceMap.() -> T) = browse()

    companion object {
        class Builder {
            class IncompleteSourceMapException : Exception("Please make sure your source map is complete")

            private interface Class {
                var identifier: String?
            }

            class Net {
                class Minecraft {
                    class Network {
                        class Play {
                            class ServerPlayNetHandlerClass : Class {
                                override var identifier: String? = null
                                var handleChat: String? = null
                                var handleCommand: String? = null
                                var player: String? = null
                                var handleCustomCommandSuggestions: String? = null

                                operator fun invoke(
                                    identifier: String,
                                    handleChat: String,
                                    handleCommand: String,
                                    player: String,
                                    handleCustomCommandSuggestions: String
                                ) {
                                    this.identifier = identifier
                                    this.handleChat = handleChat
                                    this.handleCommand = handleCommand
                                    this.player = player
                                    this.handleCustomCommandSuggestions = handleCustomCommandSuggestions
                                }
                            }

                            class Client {
                                class CTabCompletePacketClass : Class {
                                    override var identifier: String? = null

                                    operator fun invoke(identifier: String) {
                                        this.identifier = identifier
                                    }
                                }

                                val CTabCompletePacket = CTabCompletePacketClass()
                            }

                            val ServerPlayNetHandler = ServerPlayNetHandlerClass()
                            val client = Client()
                        }

                        val play = Play()
                    }

                    class Entity {
                        class Player {
                            class ServerPlayerEntityClass : Class {
                                override var identifier: String? = null
                                var resetLastActionTime: String? = null
                                var sendMessage: String? = null

                                operator fun invoke(
                                    identifier: String,
                                    resetLastActionTime: String,
                                    sendMessage: String
                                ) {
                                    this.identifier = identifier
                                    this.resetLastActionTime = resetLastActionTime
                                    this.sendMessage = sendMessage
                                }
                            }

                            val ServerPlayerEntity = ServerPlayerEntityClass()
                        }

                        class LivingEntityClass : Class {
                            override var identifier: String? = null
                            var getHealth: String? = null
                            var setHealth: String? = null

                            operator fun invoke(identifier: String, getHealth: String, setHealth: String) {
                                this.identifier = identifier
                                this.getHealth = getHealth
                                this.setHealth = setHealth
                            }
                        }

                        val player = Player()
                        val LivingEntity = LivingEntityClass()
                    }

                    class Server {
                        class Management {
                            class PlayerListClass : Class {
                                override var identifier: String? = null

                                var broadcastMessage: String? = null

                                operator fun invoke(identifier: String, broadcastMessage: String) {
                                    this.identifier = identifier
                                    this.broadcastMessage = broadcastMessage
                                }
                            }

                            val PlayerList = PlayerListClass()
                        }

                        val management = Management()
                    }

                    class Util {
                        class Text {
                            class ChatTypeClass : Class {
                                override var identifier: String? = null
                                var CHAT: String? = null
                                var SYSTEM: String? = null
                                var GAME_INFO: String? = null

                                operator fun invoke(
                                    identifier: String,
                                    CHAT: String,
                                    SYSTEM: String,
                                    GAME_INFO: String
                                ) {
                                    this.identifier = identifier
                                    this.CHAT = CHAT
                                    this.SYSTEM = SYSTEM
                                    this.GAME_INFO = GAME_INFO
                                }
                            }

                            class StringTextComponentClass : Class {
                                override var identifier: String? = null

                                operator fun invoke(identifier: String) {
                                    this.identifier = identifier
                                }
                            }

                            val ChatType = ChatTypeClass()
                            val StringTextComponent = StringTextComponentClass()
                        }

                        val text = Text()
                    }

                    class Command {
                        class CommandsClass : Class {
                            override var identifier: String? = null
                            var fillUsableCommands: String? = null

                            operator fun invoke(
                                identifier: String,
                                fillUsableCommands: String
                            ) {
                                this.identifier = identifier
                                this.fillUsableCommands = fillUsableCommands
                            }
                        }

                        class CommandSourceClass : Class {
                            override var identifier: String? = null
                            var source: String? = null
                            var getPlayerOrException: String? = null
                            var getEntity: String? = null

                            operator fun invoke(
                                identifier: String,
                                source: String,
                                getPlayerOrException: String,
                                getEntity: String
                            ) {
                                this.identifier = identifier
                                this.source = source
                                this.getPlayerOrException = getPlayerOrException
                                this.getEntity = getEntity
                            }
                        }

                        class Arguments {
                            class EntityArgumentClass : Class {
                                override var identifier: String? = null
                                var entity: String? = null
                                var entities: String? = null

                                operator fun invoke(identifier: String, entity: String, entities: String) {
                                    this.identifier = identifier
                                    this.entity = entity
                                    this.entities = entities
                                }
                            }

                            val EntityArgument = EntityArgumentClass()
                        }

                        val Commands = CommandsClass()
                        val CommandSource = CommandSourceClass()
                        val arguments = Arguments()
                    }

                    val server = Server()
                    val network = Network()
                    val entity = Entity()
                    val util = Util()
                    val command = Command()
                }

                val minecraft = Minecraft()
            }

            val net = Net()

            fun build(): SourceMap = SourceMap(
                Net(
                    SourceMap.Net.Minecraft(
                        SourceMap.Net.Minecraft.Network(
                            SourceMap.Net.Minecraft.Network.Play(
                                SourceMap.Net.Minecraft.Network.Play.ServerPlayNetHandlerClass(
                                    net.minecraft.network.play.ServerPlayNetHandler.identifier
                                        ?: throw IncompleteSourceMapException(),
                                    net.minecraft.network.play.ServerPlayNetHandler.handleChat
                                        ?: throw IncompleteSourceMapException(),
                                    net.minecraft.network.play.ServerPlayNetHandler.handleCommand
                                        ?: throw IncompleteSourceMapException(),
                                    net.minecraft.network.play.ServerPlayNetHandler.player
                                        ?: throw IncompleteSourceMapException(),
                                    net.minecraft.network.play.ServerPlayNetHandler.handleCustomCommandSuggestions
                                        ?: throw IncompleteSourceMapException(),
                                ),
                                SourceMap.Net.Minecraft.Network.Play.Client(
                                    SourceMap.Net.Minecraft.Network.Play.Client.CTabCompletePacketClass(
                                        net.minecraft.network.play.client.CTabCompletePacket.identifier
                                            ?: throw IncompleteSourceMapException()
                                    )
                                )
                            )
                        ),
                        SourceMap.Net.Minecraft.Entity(
                            SourceMap.Net.Minecraft.Entity.Player(
                                SourceMap.Net.Minecraft.Entity.Player.ServerPlayerEntityClass(
                                    net.minecraft.entity.player.ServerPlayerEntity.identifier
                                        ?: throw IncompleteSourceMapException(),
                                    net.minecraft.entity.player.ServerPlayerEntity.resetLastActionTime
                                        ?: throw IncompleteSourceMapException(),
                                    net.minecraft.entity.player.ServerPlayerEntity.sendMessage
                                        ?: throw IncompleteSourceMapException(),
                                )
                            ),
                            SourceMap.Net.Minecraft.Entity.LivingEntityClass(
                                net.minecraft.entity.LivingEntity.identifier
                                    ?: throw IncompleteSourceMapException(),
                                net.minecraft.entity.LivingEntity.getHealth
                                    ?: throw IncompleteSourceMapException(),
                                net.minecraft.entity.LivingEntity.setHealth
                                    ?: throw IncompleteSourceMapException()
                            )
                        ),
                        SourceMap.Net.Minecraft.Server(
                            SourceMap.Net.Minecraft.Server.Management(
                                SourceMap.Net.Minecraft.Server.Management.PlayerListClass(
                                    net.minecraft.server.management.PlayerList.identifier
                                        ?: throw IncompleteSourceMapException(),
                                    net.minecraft.server.management.PlayerList.broadcastMessage
                                        ?: throw IncompleteSourceMapException()
                                )
                            )
                        ),
                        SourceMap.Net.Minecraft.Util(
                            SourceMap.Net.Minecraft.Util.Text(
                                SourceMap.Net.Minecraft.Util.Text.ChatTypeClass(
                                    net.minecraft.util.text.ChatType.identifier ?: throw IncompleteSourceMapException(),
                                    net.minecraft.util.text.ChatType.CHAT ?: throw IncompleteSourceMapException(),
                                    net.minecraft.util.text.ChatType.SYSTEM ?: throw IncompleteSourceMapException(),
                                    net.minecraft.util.text.ChatType.GAME_INFO ?: throw IncompleteSourceMapException(),
                                ),
                                SourceMap.Net.Minecraft.Util.Text.StringTextComponentClass(
                                    net.minecraft.util.text.StringTextComponent.identifier
                                        ?: throw IncompleteSourceMapException()
                                )
                            )
                        ),
                        SourceMap.Net.Minecraft.Command(
                            SourceMap.Net.Minecraft.Command.CommandsClass(
                                net.minecraft.command.Commands.identifier ?: throw IncompleteSourceMapException(),
                                net.minecraft.command.Commands.fillUsableCommands
                                    ?: throw IncompleteSourceMapException()
                            ),
                            SourceMap.Net.Minecraft.Command.CommandSourceClass(
                                net.minecraft.command.CommandSource.identifier ?: throw IncompleteSourceMapException(),
                                net.minecraft.command.CommandSource.source ?: throw IncompleteSourceMapException(),
                                net.minecraft.command.CommandSource.getPlayerOrException
                                    ?: throw IncompleteSourceMapException(),
                                net.minecraft.command.CommandSource.getEntity ?: throw IncompleteSourceMapException(),
                            ),
                            SourceMap.Net.Minecraft.Command.Arguments(
                                SourceMap.Net.Minecraft.Command.Arguments.EntityArgumentClass(
                                    net.minecraft.command.arguments.EntityArgument.identifier
                                        ?: throw IncompleteSourceMapException(),
                                    net.minecraft.command.arguments.EntityArgument.entity
                                        ?: throw IncompleteSourceMapException(),
                                    net.minecraft.command.arguments.EntityArgument.entities
                                        ?: throw IncompleteSourceMapException(),
                                )
                            )
                        )
                    )
                )
            )
        }

        fun create(configure: Builder.() -> Unit): SourceMap {
            val builder = Builder()
            builder.configure()

            return builder.build()
        }
    }
}
