package xyz.deftu.coffeecord.interactions;

import xyz.deftu.coffeecord.DiscordClient;
import xyz.deftu.coffeecord.entities.message.Message;
import xyz.deftu.coffeecord.requests.types.interactions.InteractionEditRequest;

public class InteractionDeferred {

    private final DiscordClient client;
    private final Interaction interaction;

    public InteractionDeferred(DiscordClient client, Interaction interaction) {
        this.client = client;
        this.interaction = interaction;
    }

    public void edit(Message message) {
        client.getRestRequester().request(new InteractionEditRequest(client, interaction, message));
    }

}