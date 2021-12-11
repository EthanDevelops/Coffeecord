package xyz.deftu.coffeecord.events.interactions;

import xyz.deftu.coffeecord.DiscordClient;
import xyz.deftu.coffeecord.events.GenericEvent;

public class GenericInteractionEvent extends GenericEvent {

    public GenericInteractionEvent(DiscordClient client) {
        super(client);
    }

}