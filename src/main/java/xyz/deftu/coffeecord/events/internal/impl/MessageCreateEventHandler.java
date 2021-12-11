package xyz.deftu.coffeecord.events.internal.impl;

import com.google.gson.JsonObject;
import xyz.deftu.coffeecord.DiscordClient;
import xyz.deftu.coffeecord.events.messages.MessageReceivedEvent;
import xyz.deftu.coffeecord.events.internal.BaseEventHandler;

public class MessageCreateEventHandler extends BaseEventHandler {

    public MessageCreateEventHandler(DiscordClient client) {
        super(client);
    }

    public void handle(JsonObject data) {
        client.getEventBus().post(new MessageReceivedEvent(client, client.getEntityCreator().createMessage(data)));
    }

}