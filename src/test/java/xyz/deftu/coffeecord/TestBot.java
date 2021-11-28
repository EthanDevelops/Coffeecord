package xyz.deftu.coffeecord;

import xyz.deftu.coffeecord.events.MessageReceivedEvent;
import xyz.qalcyo.eventbus.SubscribeEvent;

public class TestBot {

    private final DiscordClient client = new DiscordClient();

    public void start() {
        client.login(System.getProperty("coffeecord.test.token")/* Set the bot token in JVM arguments. */);
        client.getEventBus().register(this);
    }

    @SubscribeEvent
    private void onMessage(MessageReceivedEvent event) {
        System.out.println(event.message.getId());
    }

    public static void main(String[] args) {
        new TestBot().start();
    }

}