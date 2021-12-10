package xyz.deftu.coffeecord.entities.user;

import xyz.deftu.coffeecord.DiscordClient;
import xyz.deftu.coffeecord.entities.guild.Guild;
import xyz.deftu.coffeecord.requests.types.SelfGuildsRequest;

import java.util.ArrayList;
import java.util.List;

public class SelfUser {

    private final DiscordClient client;
    private final List<Guild> guildCache = new ArrayList<>();

    public SelfUser(DiscordClient client) {
        this.client = client;
    }

    public List<Guild> getGuilds() {
        List<Guild> value = client.getRestRequester().request(new SelfGuildsRequest(client));
        if (guildCache.isEmpty()) {
            updateGuildCache(value);
        }

        return value;
    }

    public List<Guild> getGuildCache() {
        if (guildCache.isEmpty())
            return getGuilds();
        return guildCache;
    }

    public void updateGuildCache(List<Guild> guilds) {
        guildCache.addAll(guilds);
    }

}