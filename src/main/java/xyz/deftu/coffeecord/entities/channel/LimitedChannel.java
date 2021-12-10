package xyz.deftu.coffeecord.entities.channel;

import xyz.deftu.coffeecord.DiscordClient;

public class LimitedChannel extends BaseChannel {

    private final long id;

    public LimitedChannel(DiscordClient client, long id) {
        super(client);
        this.id = id;
    }

    public long getId() {
        return id;
    }

}