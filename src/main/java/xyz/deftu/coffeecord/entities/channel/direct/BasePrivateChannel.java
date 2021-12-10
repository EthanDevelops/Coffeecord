package xyz.deftu.coffeecord.entities.channel.direct;

import xyz.deftu.coffeecord.DiscordClient;
import xyz.deftu.coffeecord.entities.channel.BaseChannel;
import xyz.deftu.coffeecord.entities.message.Message;
import xyz.deftu.coffeecord.requests.types.ChannelMessageRequest;

public abstract class BasePrivateChannel extends BaseChannel {

    private final long id;
    private final long lastMessageId;

    public BasePrivateChannel(DiscordClient client, long id, long lastMessageId) {
        super(client);
        this.id = id;
        this.lastMessageId = lastMessageId;
    }

    public long getId() {
        return id;
    }

    public long getLastMessageId() {
        return lastMessageId;
    }

    public Message getLastMessage() {
        if (lastMessageId == -1)
            return null;
        return client.getRestRequester().request(new ChannelMessageRequest(client, id, lastMessageId));
    }

}