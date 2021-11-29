package xyz.deftu.coffeecord;

import xyz.deftu.coffeecord.entities.message.Message;
import xyz.deftu.coffeecord.entities.message.MessageReference;
import xyz.deftu.coffeecord.events.MessageReceivedEvent;
import xyz.deftu.coffeecord.socket.GatewayIntent;
import xyz.qalcyo.eventbus.SubscribeEvent;

import java.time.OffsetDateTime;

public class TestBot {

    private final DiscordClient client = new DiscordClient();

    public void start() {
        client.addIntents(GatewayIntent.GUILD_MESSAGES);
        client.login(System.getProperty("coffeecord.test.token")/* Set the bot token in JVM arguments. */);
        client.getEventBus().register(this);
    }

    @SubscribeEvent
    private void onMessage(MessageReceivedEvent event) {
        event.message.reply(new Message(event.client, false, OffsetDateTime.now(), false, -1, OffsetDateTime.now(), "Fuck you.",  new MessageReference(-1, -1, -1), -1), 690263476089782428L);
    }

    public static void main(String[] args) {
        new TestBot().start();
    }

}