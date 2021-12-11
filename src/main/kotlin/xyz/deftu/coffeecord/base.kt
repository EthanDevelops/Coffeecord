package xyz.deftu.coffeecord

import xyz.deftu.coffeecord.presence.Activity
import xyz.deftu.coffeecord.presence.OnlineStatus
import xyz.deftu.coffeecord.socket.GatewayIntent

fun discord(builder: DiscordClientBuilder.() -> Unit): DiscordClient = DiscordClientBuilder().apply(builder).build()

class DiscordClientBuilder {
    var token: String? = null
    var intents: List<GatewayIntent>? = null
    var onlineStatus: OnlineStatus? = OnlineStatus.ONLINE
    var activities: Array<Activity>? = null
    var eventListeners: Array<Any>? = null
    fun build(): DiscordClient {
        val client = DiscordClient()
        if (token.isNullOrEmpty())
            throw IllegalStateException("Token cannot be null or empty!")
        if (intents != null)
            client.addIntents(intents)
        if (eventListeners != null) {
            for (item in eventListeners!!) {
                client.eventBus.register(item)
            }
        }
        client.presence.onlineStatus = onlineStatus
        if (activities != null) {
            for (activity in activities!!) {
                client.presence.addActivity(activity)
            }
        }

        client.token = token
        return client
    }
}