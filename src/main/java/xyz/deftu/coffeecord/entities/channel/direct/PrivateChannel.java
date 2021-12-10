package xyz.deftu.coffeecord.entities.channel.direct;

import xyz.deftu.coffeecord.DiscordClient;
import xyz.deftu.coffeecord.entities.message.Message;
import xyz.deftu.coffeecord.entities.user.User;
import xyz.deftu.coffeecord.requests.types.MessageSendRequest;

public class PrivateChannel extends BasePrivateChannel {

    private final User user;

    public PrivateChannel(DiscordClient client, long id, long lastMessageId, User user) {
        super(client, id, lastMessageId);
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public final Message send(Message message) {
        return client.getRestRequester().request(new MessageSendRequest(
                client,
                message,
                getId()
        ));
    }

}