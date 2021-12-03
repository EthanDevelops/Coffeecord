package xyz.deftu.coffeecord;

import xyz.deftu.coffeecord.entities.message.MessageBuilder;
import xyz.deftu.coffeecord.entities.message.embed.MessageEmbedBuilder;
import xyz.deftu.coffeecord.events.MessageReceivedEvent;
import xyz.deftu.coffeecord.socket.GatewayIntent;
import xyz.deftu.eventbus.SubscribeEvent;

import java.awt.*;

public class TestBot {

    private final DiscordClient client = new DiscordClient();

    public void start() {
        client.addIntents(GatewayIntent.GUILD_MESSAGES);
        client.login(System.getProperty("coffeecord.test.token")/* Set the bot token in JVM arguments. */);
        client.getEventBus().register(this);
    }

    @SubscribeEvent
    private void onMessage(MessageReceivedEvent event) {
        if (!event.message.getAuthor().isBot()) {
            event.message.reply(new MessageBuilder()
                    .addEmbed(new MessageEmbedBuilder()
                            .setTitle("Cringe.")
                            .setColour(new Color(255, 94, 94))
                            .addField("Test", "Hi! I'm a test field.")
                            .setFooter("Hello.")
                            .setImage("https://imagesvc.meredithcorp.io/v3/mm/image?url=https%3A%2F%2Fstatic.onecms.io%2Fwp-content%2Fuploads%2Fsites%2F13%2F2015%2F04%2F05%2Ffeatured.jpg&q=85")
                            .build())
                    .build(), 690263476089782428L);
        }
    }

    public static void main(String[] args) {
        new TestBot().start();
    }

}