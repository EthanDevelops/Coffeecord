package xyz.deftu.coffeecord

import xyz.deftu.coffeecord.events.MessageReceivedEvent
import xyz.deftu.coffeecord.socket.GatewayIntent
import xyz.deftu.eventbus.SubscribeEvent

class TestBotKt {

    val client = discord {
        token = System.getProperty("coffeecord.test.token") /* Set the bot token in JVM arguments. */
        intents = listOf(GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_PRESENCES, GatewayIntent.GUILD_MESSAGES)
        eventListeners = arrayOf(this@TestBotKt)
    }

    fun start() {
        client.login()
    }

    @SubscribeEvent
    private fun onMessageReceived(event: MessageReceivedEvent) {
        println(event.message.author.avatarUrl)
        if (!event.message.author.isBot) {
            event.message.reply(message {
                content = "Your avatar url is ${event.message.author.avatarUrl}"
            }, 690263476089782428)
        }
    }

}

fun main() {
    TestBotKt().start()
}