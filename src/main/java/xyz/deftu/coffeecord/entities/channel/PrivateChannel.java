package xyz.deftu.coffeecord.entities.channel;

import xyz.deftu.coffeecord.DiscordClient;
import xyz.deftu.coffeecord.entities.user.User;

public class PrivateChannel extends BaseChannel {

    private final User user;

    public PrivateChannel(DiscordClient client, User user) {
        super(client);
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public long getId() {
        return user.getId();
    }

}