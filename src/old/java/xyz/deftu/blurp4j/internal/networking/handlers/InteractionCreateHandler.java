package xyz.deftu.blurp4j.internal.networking.handlers;

import xyz.deftu.blurp4j.DiscordClient;
import xyz.deftu.blurp4j.entities.messages.components.MessageComponent;
import xyz.deftu.blurp4j.events.ButtonClickEvent;
import xyz.deftu.blurp4j.events.SelectionMenuEvent;
import xyz.deftu.blurp4j.events.SlashCommandEvent;
import xyz.deftu.blurp4j.interactions.InteractionType;
import xyz.deftu.blurp4j.internal.networking.DiscordSocket;
import xyz.deftu.blurp4j.internal.networking.DiscordSocketHandler;
import xyz.qalcyo.json.entities.JsonObject;

public class InteractionCreateHandler extends DiscordSocketHandler {

    public InteractionCreateHandler(DiscordSocket socket, DiscordClient client) {
        super(socket, client);
    }

    public void internal(JsonObject object) {
        JsonObject data = object.getAsObject("d");
        int type = data.getAsInt("type");
        if (data.getAsInt("version") != 1) {
            client.getLogger().error("Received interaction with version {}, this version is not supported by the current version of DiscordTGM. Please update if you'd like to continue!", object.getAsInt("version"));
            return;
        }

        InteractionType interactionType = InteractionType.fromKey(type);
        if (interactionType == null)
            return;
        switch (interactionType) {
            case SLASH_COMMAND:
                command(data);
                break;
            case COMPONENT:
                component(data);
                break;
        }
    }

    private void command(JsonObject object) {
        client.getEventBus().call(new SlashCommandEvent(client, object));
    }

    private void component(JsonObject object) {
        switch (MessageComponent.MessageComponentType.fromKey(object.getAsObject("data").getAsInt("component_type"))) {
            case BUTTON:
                client.getEventBus().call(new ButtonClickEvent(client, object));
                break;
            case SELECTION_MENU:
                client.getEventBus().call(new SelectionMenuEvent(client, object));
                break;
        }
    }

}