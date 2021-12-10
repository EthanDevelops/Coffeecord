package xyz.deftu.coffeecord.entities.channel;

import xyz.deftu.coffeecord.DiscordClient;
import xyz.deftu.coffeecord.entities.channel.guild.GuildCategory;
import xyz.deftu.coffeecord.entities.guild.Guild;
import xyz.deftu.coffeecord.entities.guild.GuildPermission;
import xyz.deftu.coffeecord.requests.types.ChannelRequest;
import xyz.deftu.coffeecord.requests.types.GuildRequest;

import java.util.List;

public abstract class GuildChannel extends BaseChannel {

    private final long id;
    private final Guild guild;

    private final String name;
    private final int position;
    private final List<GuildPermission> permissionOverwrites;
    private final long parentId;
    private GuildCategory parentCache;

    public GuildChannel(DiscordClient client, long id, Guild guild, String name, int position, List<GuildPermission> permissionOverwrites, long parentId) {
        super(client);
        this.id = id;
        this.guild = guild;
        this.name = name;
        this.position = position;
        this.permissionOverwrites = permissionOverwrites;
        this.parentId = parentId;
    }

    public GuildChannel(DiscordClient client, long id, long guildId, String name, int position, List<GuildPermission> permissionOverwrites, long parentId) {
        this(client, id, client.getRestRequester().request(new GuildRequest(client, guildId)), name, position, permissionOverwrites, parentId);
    }

    public long getId() {
        return id;
    }

    public Guild getGuild() {
        return guild;
    }

    public String getName() {
        return name;
    }

    public int getPosition() {
        return position;
    }

    public List<GuildPermission> getPermissionOverwrites() {
        return permissionOverwrites;
    }

    public long getParentId() {
        return parentId;
    }

    public GuildCategory getParent() {
        if (parentId == -1)
            return null;
        GuildCategory value = (GuildCategory) client.getRestRequester().request(new ChannelRequest(client, parentId));
        if (parentCache == null)
            parentCache = value;
        return value;
    }

    public GuildCategory getParentCache() {
        if (parentCache == null)
            return getParent();
        return parentCache;
    }

}