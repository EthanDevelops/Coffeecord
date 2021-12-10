package xyz.deftu.coffeecord.entities.channel.guild;

import xyz.deftu.coffeecord.DiscordClient;
import xyz.deftu.coffeecord.entities.channel.GuildChannel;
import xyz.deftu.coffeecord.entities.guild.Guild;
import xyz.deftu.coffeecord.entities.guild.GuildPermission;

import java.util.List;

public class GuildCategory extends GuildChannel {

    public GuildCategory(DiscordClient client, long id, Guild guild, String name, int position, List<GuildPermission> permissionOverwrites) {
        super(client, id, guild, name, position, permissionOverwrites, -1);
    }

    public GuildCategory(DiscordClient client, long id, long guildId, String name, int position, List<GuildPermission> permissionOverwrites) {
        super(client, id, guildId, name, position, permissionOverwrites, -1);
    }

}