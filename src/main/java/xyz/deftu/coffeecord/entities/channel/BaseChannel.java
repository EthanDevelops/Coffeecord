package xyz.deftu.coffeecord.entities.channel;

import xyz.deftu.coffeecord.DiscordClient;
import xyz.deftu.coffeecord.entities.IMentionable;
import xyz.deftu.coffeecord.entities.ISnowflake;
import xyz.deftu.coffeecord.entities.message.Message;
import xyz.deftu.coffeecord.requests.types.MessageSendRequest;

public abstract class BaseChannel implements ISnowflake, IMentionable {

    private final DiscordClient client;

    public BaseChannel(DiscordClient client) {
        this.client = client;
    }

    public final String asMention() {
        return String.format("<#%s>", getId());
    }

    public final DiscordClient getClient() {
        return client;
    }

    public final Message send(Message message) {
        DiscordClient client = getClient();
        return client.getRestRequester().request(new MessageSendRequest(
                client,
                message,
                getId()
        ));
    }

}