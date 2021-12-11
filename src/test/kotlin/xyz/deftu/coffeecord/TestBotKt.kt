package xyz.deftu.coffeecord

import xyz.deftu.coffeecord.entities.channel.GuildChannel
import xyz.deftu.coffeecord.entities.channel.direct.PrivateChannel
import xyz.deftu.coffeecord.entities.channel.guild.GuildTextChannel
import xyz.deftu.coffeecord.events.MessageReceivedEvent
import xyz.deftu.coffeecord.presence.Activity
import xyz.deftu.coffeecord.presence.OnlineStatus
import xyz.deftu.coffeecord.socket.GatewayIntent
import xyz.deftu.eventbus.SubscribeEvent

class TestBotKt {

    val client by lazy {
        discord {
            token = System.getProperty("coffeecord.test.token") /* Set the bot token in JVM arguments. */
            intents = listOf(GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_MESSAGES, GatewayIntent.DIRECT_MESSAGES)
            onlineStatus = OnlineStatus.DO_NOT_DISTURB
            activities = arrayOf(Activity.playing("with Deftu's mind."))
            eventListeners = arrayOf(this@TestBotKt)
        }
    }

    fun start() {
        client.login()
    }

    @SubscribeEvent
    private fun onMessageReceived(event: MessageReceivedEvent) {
        if (!event.message.author.isBot) {
            event.reply(message {
                content = "Hello!"
            })
        }
    }

}

fun main() {
    TestBotKt().start()
}