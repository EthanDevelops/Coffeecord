package xyz.deftu.coffeecord.events;

import xyz.deftu.coffeecord.DiscordClient;
import xyz.deftu.eventbus.Event;

public class GenericEvent extends Event {
    public final DiscordClient client;
    public GenericEvent(DiscordClient client) {
        this.client = client;
    }
}