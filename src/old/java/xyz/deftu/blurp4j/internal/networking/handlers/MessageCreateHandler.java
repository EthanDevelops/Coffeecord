package xyz.deftu.blurp4j.internal.networking.handlers;

import xyz.deftu.blurp4j.DiscordClient;
import xyz.deftu.blurp4j.entities.channels.TextChannel;
import xyz.deftu.blurp4j.entities.messages.Message;
import xyz.deftu.blurp4j.events.MessageCreateEvent;
import xyz.deftu.blurp4j.internal.networking.DiscordSocket;
import xyz.deftu.blurp4j.internal.networking.DiscordSocketHandler;
import xyz.qalcyo.json.entities.JsonObject;

public class MessageCreateHandler extends DiscordSocketHandler {

    public MessageCreateHandler(DiscordSocket socket, DiscordClient client) {
        super(socket, client);
    }

    public void internal(JsonObject object) {
        JsonObject data = object.getAsObject("d");
        Message message;

        try {
            message = client.getEntityCreator().createMessage(data);
        } catch (Exception e) {
            e.printStackTrace();
            message = null;
        }

        client.getEventBus().call(new MessageCreateEvent(client, new TextChannel(data.getAsLong("guild_id"), data.getAsLong("channel_id")), message, null));
    }

}