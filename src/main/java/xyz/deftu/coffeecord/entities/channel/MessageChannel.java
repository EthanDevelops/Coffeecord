package xyz.deftu.coffeecord.entities.channel;

import xyz.deftu.coffeecord.DiscordClient;

public class MessageChannel implements IChannel {

    private final DiscordClient client;
    private long id = -1;

    public MessageChannel(DiscordClient client) {
        this.client = client;
    }

    public String asMention() {
        return "";
    }

    public long getId() {
        return id;
    }

    public DiscordClient getClient() {
        return client;
    }

}