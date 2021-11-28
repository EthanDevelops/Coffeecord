package xyz.deftu.blurp4j.events;

import xyz.deftu.blurp4j.DiscordClient;
import xyz.qalcyo.simpleeventbus.Event;

public class GenericEvent extends Event {
    public final DiscordClient client;
    public GenericEvent(DiscordClient client) {
        this.client = client;
    }
}