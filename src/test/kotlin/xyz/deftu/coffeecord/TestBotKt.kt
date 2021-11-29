package xyz.deftu.coffeecord

import xyz.deftu.coffeecord.events.MessageReceivedEvent
import xyz.deftu.coffeecord.socket.GatewayIntent
import xyz.qalcyo.eventbus.SubscribeEvent

class TestBotKt {

    val client = discord {
        token = System.getProperty("coffeecord.test.token") /* Set the bot token in JVM arguments. */
        intents = listOf(GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_PRESENCES)
        eventListeners = arrayOf(this@TestBotKt)
    }

    fun start() {
        client.login()
    }

    @SubscribeEvent
    private fun onMessageReceived(event: MessageReceivedEvent) {
        println(event.message)
    }

}

fun main() {
    TestBotKt().start()
}