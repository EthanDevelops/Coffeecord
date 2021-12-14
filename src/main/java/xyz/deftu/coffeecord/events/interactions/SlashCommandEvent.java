package xyz.deftu.coffeecord.events.interactions;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import xyz.deftu.coffeecord.DiscordClient;
import xyz.deftu.coffeecord.commands.impl.slash.SlashCommand;
import xyz.deftu.coffeecord.entities.message.Message;
import xyz.deftu.coffeecord.interactions.Interaction;
import xyz.deftu.coffeecord.interactions.InteractionDeferred;
import xyz.deftu.coffeecord.interactions.InteractionResponse;
import xyz.deftu.coffeecord.interactions.InteractionResponseType;
import xyz.deftu.coffeecord.requests.types.interactions.InteractionRespondRequest;

import java.util.Map;

public class SlashCommandEvent extends GenericInteractionEvent {

    public final SlashCommand command;

    public SlashCommandEvent(DiscordClient client, Interaction interaction, SlashCommand command) {
        super(client, interaction);
        this.command = command;
    }

    public void reply(Message message, boolean ephemeral) {
        JsonObject data = new JsonObject();
        for (Map.Entry<String, JsonElement> entry : message.asJson().entrySet())
            data.add(entry.getKey(), entry.getValue());
        if (ephemeral) {
            data.addProperty("flags", 1 << 6);
        }

        client.getRestRequester().request(new InteractionRespondRequest(client, interaction, new InteractionResponse(InteractionResponseType.CHANNEL_MESSAGE_WITH_SOURCE, data)));
    }

    public void reply(Message message) {
        reply(message, false);
    }

    public InteractionDeferred defer(boolean ephemeral) {
        JsonObject data = new JsonObject();
        if (ephemeral) {
            data.addProperty("flags", 1 << 6);
        }

        client.getRestRequester().request(new InteractionRespondRequest(client, interaction, new InteractionResponse(InteractionResponseType.DEFERRED_CHANNEL_MESSAGE_WITH_SOURCE, data)));
        return new InteractionDeferred(client, interaction);
    }

    public InteractionDeferred defer() {
        return defer(false);
    }

}