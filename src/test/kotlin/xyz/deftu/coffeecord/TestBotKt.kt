package xyz.deftu.coffeecord

import com.google.gson.JsonObject
import xyz.deftu.coffeecord.entities.channel.direct.PrivateChannel
import xyz.deftu.coffeecord.entities.channel.guild.GuildTextChannel
import xyz.deftu.coffeecord.events.MessageReceivedEvent
import xyz.deftu.coffeecord.events.internal.BaseEventHandler
import xyz.deftu.coffeecord.socket.GatewayIntent
import xyz.deftu.eventbus.SubscribeEvent

class TestBotKt {

    val client by lazy {
        discord {
            token = System.getProperty("coffeecord.test.token") /* Set the bot token in JVM arguments. */
            intents = listOf(GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_PRESENCES, GatewayIntent.GUILD_MESSAGES, GatewayIntent.DIRECT_MESSAGES)
            eventListeners = arrayOf(this@TestBotKt)
        }
    }

    fun start() {
        client.eventParser.addHandler("MESSAGE_CREATE", object : BaseEventHandler(client) {
            override fun handle(data: JsonObject?) {
                if (data != null) {
                    println(client.gson.toJson(data))
                }
            }
        })
        client.login()
    }

    @SubscribeEvent
    private fun onMessageReceived(event: MessageReceivedEvent) {
        if (!event.message.author.isBot) {
            val cont = event.message.content
            if (cont.startsWith("!")) {
                val command = cont.substring(1).lowercase()
                when (command) {
                    "test" -> {
                        when (event.channel::class.java) {
                            PrivateChannel::class.java -> {
                                event.reply(message {
                                    content = "Hello from DMs!"
                                })
                            }

                            GuildTextChannel::class.java -> {
                                event.reply(message {
                                    content = "Hello from a guild text channel!"
                                })
                            }

                            else -> {
                                event.reply(message {
                                    content = "This channel is not supported. (${event.channel::class.java.name})"
                                })
                            }
                        }
                    }

                    "guilds" -> {
                        val guilds = event.client.selfUser.guilds
                        for (guild in guilds) {
                            println(guild.features)
                        }
                    }

                    "username" -> {
                        event.client.selfApplication.setUsername("Deftu's Test Bot")
                        event.reply(message {
                            content = "My username has been updated!"
                        })
                    }

                    else -> {
                        event.reply(message {
                            content = "Invalid command."
                        })
                    }
                }
            }
        }
    }

}

fun main() {
    TestBotKt().start()
}