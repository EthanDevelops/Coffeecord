package xyz.deftu.coffeecord.events.interactions;

import xyz.deftu.coffeecord.DiscordClient;
import xyz.deftu.coffeecord.events.GenericEvent;
import xyz.deftu.coffeecord.interactions.Interaction;

public class GenericInteractionEvent extends GenericEvent {

    public final Interaction interaction;

    public GenericInteractionEvent(DiscordClient client, Interaction interaction) {
        super(client);
        this.interaction = interaction;
    }

}