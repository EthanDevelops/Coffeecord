package xyz.deftu.coffeecord;

import xyz.deftu.coffeecord.events.messages.MessageReceivedEvent;
import xyz.deftu.coffeecord.socket.GatewayIntent;
import xyz.deftu.eventbus.SubscribeEvent;

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
            System.out.println(event.message.getChannel());
        }
    }

    public static void main(String[] args) {
        new TestBot().start();
    }

}