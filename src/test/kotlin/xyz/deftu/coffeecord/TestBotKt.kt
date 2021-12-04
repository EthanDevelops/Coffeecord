package xyz.deftu.coffeecord

import xyz.deftu.coffeecord.events.MessageReceivedEvent
import xyz.deftu.coffeecord.socket.GatewayIntent
import xyz.deftu.eventbus.SubscribeEvent
import java.time.OffsetDateTime

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
        if (!event.message.author.isBot) {
            event.message.reply(message {
                embeds = listOf(
                    embed {
                        title = "Test"
                        description = "Test description"
                        url = "https://deftu.xyz/"
                        timestamp = OffsetDateTime.now()


                        footer = embedFooter {
                            text = "Hello! This bot was made using the Coffeecord library."
                        }

                        image = embedMedia {
                            url = "https://imagesvc.meredithcorp.io/v3/mm/image?url=https%3A%2F%2Fstatic.onecms.io%2Fwp-content%2Fuploads%2Fsites%2F13%2F2015%2F04%2F05%2Ffeatured.jpg&q=85"
                        }

                        thumbnail = embedMedia {
                            url = "https://lh3.googleusercontent.com/proxy/FNlhQZ5RIU03NDykNHKuahZCSQb-wexoJGJgh92YVyuvzdRrgvXiAB6Krltg5t56sZw9i7kn3tEt7bbOMaHAiEfD7L7sZns"
                        }

                        author = embedAuthor {
                            name = "Deftu"
                        }

                        fields = listOf(
                            embedField {
                                name = "Field 1"
                                value = "This is a Kotlin test with fields."
                            },
                            embedField {
                                name = "Field 2"
                                value = "If this appears, the test worked."
                            }
                        )
                    }
                )
            }, 690263476089782428)
        }
    }

}

fun main() {
    TestBotKt().start()
}