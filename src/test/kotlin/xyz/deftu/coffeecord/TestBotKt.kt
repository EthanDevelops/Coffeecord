package xyz.deftu.coffeecord

import xyz.deftu.coffeecord.entities.channel.GuildChannel
import xyz.deftu.coffeecord.entities.channel.direct.PrivateChannel
import xyz.deftu.coffeecord.entities.channel.guild.GuildTextChannel
import xyz.deftu.coffeecord.events.MessageReceivedEvent
import xyz.deftu.coffeecord.socket.GatewayIntent
import xyz.deftu.eventbus.SubscribeEvent

class TestBotKt {

    val client by lazy {
        discord {
            token = System.getProperty("coffeecord.test.token") /* Set the bot token in JVM arguments. */
            intents = listOf(GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_MESSAGES, GatewayIntent.DIRECT_MESSAGES)
            eventListeners = arrayOf(this@TestBotKt)
        }
    }

    fun start() {
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
                        var new = ""
                        for (entry in event.client.entityCache.channelCache) {
                            val channel = entry.value as GuildChannel
                            new += channel.name + " | " + channel::class.java.name + "\n"
                        }

                        event.reply(message {
                            content = new
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