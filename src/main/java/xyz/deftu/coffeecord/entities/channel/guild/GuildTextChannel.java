package xyz.deftu.coffeecord.entities.channel.guild;

import xyz.deftu.coffeecord.DiscordClient;
import xyz.deftu.coffeecord.entities.channel.GuildChannel;
import xyz.deftu.coffeecord.entities.guild.Guild;
import xyz.deftu.coffeecord.entities.Permission;
import xyz.deftu.coffeecord.entities.guild.PermissionOverwrite;
import xyz.deftu.coffeecord.entities.message.Message;
import xyz.deftu.coffeecord.requests.types.MessageSendRequest;

import java.util.List;

public class GuildTextChannel extends GuildChannel {

    private final int cooldown;
    private final boolean nsfw;
    private final String topic;

    public GuildTextChannel(DiscordClient client, long id, Guild guild, String name, int position, List<PermissionOverwrite> permissionOverwrites, long parentId, int cooldown, boolean nsfw, String topic) {
        super(client, id, guild, name, position, permissionOverwrites, parentId);
        this.cooldown = cooldown;
        this.nsfw = nsfw;
        this.topic = topic;
    }

    public GuildTextChannel(DiscordClient client, long id, long guildId, String name, int position, List<PermissionOverwrite> permissionOverwrites, long parentId, int cooldown, boolean nsfw, String topic) {
        super(client, id, guildId, name, position, permissionOverwrites, parentId);
        this.cooldown = cooldown;
        this.nsfw = nsfw;
        this.topic = topic;
    }

    public int getCooldown() {
        return cooldown;
    }

    public boolean isNsfw() {
        return nsfw;
    }

    public String getTopic() {
        return topic;
    }

    public final Message send(Message message) {
        return client.getRestRequester().request(new MessageSendRequest(
                client,
                message,
                getId()
        ));
    }

}