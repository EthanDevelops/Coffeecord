package xyz.deftu.coffeecord.events.internal.impl;

import com.google.gson.JsonObject;
import xyz.deftu.coffeecord.Coffeecord;
import xyz.deftu.coffeecord.DiscordClient;
import xyz.deftu.coffeecord.commands.ApplicationCommandType;
import xyz.deftu.coffeecord.commands.impl.slash.SlashCommand;
import xyz.deftu.coffeecord.events.interactions.SlashCommandEvent;
import xyz.deftu.coffeecord.events.internal.BaseEventHandler;
import xyz.deftu.coffeecord.interactions.Interaction;
import xyz.deftu.coffeecord.interactions.InteractionType;
import xyz.deftu.coffeecord.utils.JsonHelper;

public class InteractionCreateHandler extends BaseEventHandler {

    public InteractionCreateHandler(DiscordClient client) {
        super(client);
    }

    public void handle(JsonObject data) {
        Number typeRaw = JsonHelper.getNumber(data, "type");
        if (typeRaw != null) {
            InteractionType type = InteractionType.from(typeRaw.intValue());
            switch (type) {
                case APPLICATION_COMMAND:
                    handleApplicationCommand(data);
                    break;
                case COMPONENT:
                    handleComponent(data);
                    break;
            }
        }
    }

    private void handleApplicationCommand(JsonObject data) {
        JsonObject internalData = JsonHelper.getObject(data, "data");
        Coffeecord.createLogger().warn("Application cmd: {}", Coffeecord.GSON.toJson(data));
        Coffeecord.createLogger().warn("Application cmd int: {}", Coffeecord.GSON.toJson(internalData));
        if (internalData != null) {
            Number typeRaw = JsonHelper.getNumber(internalData, "type");
            if (typeRaw != null) {
                ApplicationCommandType type = ApplicationCommandType.from(typeRaw.intValue());
                switch (type) {
                    case CHAT:
                        handleChatCommand(data);
                        break;
                    case USER:
                        handleUserCommand(data);
                        break;
                    case MESSAGE:
                        handleMessageCommand(data);
                        break;
                }
            }
        }
    }

    private void handleChatCommand(JsonObject data) {
        SlashCommand command = client.getObjectCreator().createSlashCommand(data);
        Interaction interaction = client.getObjectCreator().createInteraction(data);
        client.getEventBus().post(new SlashCommandEvent(client, interaction, command));
    }

    private void handleUserCommand(JsonObject data) {

    }

    private void handleMessageCommand(JsonObject data) {

    }

    private void handleComponent(JsonObject data) {
        // TODO
    }

}