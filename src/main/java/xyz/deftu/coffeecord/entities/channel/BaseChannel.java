package xyz.deftu.coffeecord.entities.channel;

import xyz.deftu.coffeecord.DiscordClient;
import xyz.deftu.coffeecord.entities.IMentionable;
import xyz.deftu.coffeecord.entities.ISnowflake;

public abstract class BaseChannel implements ISnowflake, IMentionable {

    protected final DiscordClient client;

    public BaseChannel(DiscordClient client) {
        this.client = client;
    }

    public final String asMention() {
        return String.format("<#%s>", getId());
    }

    public final DiscordClient getClient() {
        return client;
    }

}