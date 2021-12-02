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
        if (event.message.messageReference != null) {
            event.message.reply(message {
                content = "Thanks!"
            }, 690263476089782428)
        }
    }

}

fun main() {
    TestBotKt().start()
}