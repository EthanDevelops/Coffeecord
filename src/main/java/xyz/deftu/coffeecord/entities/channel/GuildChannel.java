package xyz.deftu.coffeecord.entities.channel;

import xyz.deftu.coffeecord.DiscordClient;
import xyz.deftu.coffeecord.entities.guild.Guild;

public abstract class GuildChannel extends BaseChannel {

    private final long id;
    private final Guild guild;

    public GuildChannel(DiscordClient client, long id, Guild guild) {
        super(client);
        this.id = id;
        this.guild = guild;
    }

    public long getId() {
        return id;
    }

    public Guild getGuild() {
        return guild;
    }

}