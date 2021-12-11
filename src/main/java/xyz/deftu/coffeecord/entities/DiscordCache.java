package xyz.deftu.coffeecord.entities;

import com.google.gson.JsonObject;
import xyz.deftu.coffeecord.DiscordClient;
import xyz.deftu.coffeecord.entities.channel.BaseChannel;
import xyz.deftu.coffeecord.entities.guild.Guild;
import xyz.deftu.coffeecord.entities.user.User;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DiscordCache {

    private final DiscordClient client;

    private final Map<Long, User> userCache = new ConcurrentHashMap<>();
    private final Map<Long, Guild> guildCache = new HashMap<>();
    private final Map<Long, BaseChannel> channelCache = new HashMap<>();

    public DiscordCache(DiscordClient client) {
        this.client = client;
    }

    public void addUser(long id, User user) {
        userCache.put(id, user);
    }

    public void addUser(long id, JsonObject data) {
        addUser(id, client.getEntityCreator().createUser(data));
    }

    public User getUser(long id) {
        return userCache.get(id);
    }

    public void addGuild(long id, Guild guild) {
        guildCache.put(id, guild);
    }

    public void addGuild(long id, JsonObject data) {
        addGuild(id, client.getEntityCreator().createGuild(data));
    }

    public Guild getGuild(long id) {
        return guildCache.get(id);
    }

    public void addChannel(long id, BaseChannel channel) {
        channelCache.put(id, channel);
    }

    public void addChannel(long id, JsonObject data) {
        addChannel(id, client.getEntityCreator().createChannel(data));
    }

    public BaseChannel getChannel(long id) {
        return channelCache.get(id);
    }

    public Map<Long, User> getUserCache() {
        return Collections.unmodifiableMap(userCache);
    }

    public Map<Long, Guild> getGuildCache() {
        return Collections.unmodifiableMap(guildCache);
    }

    public Map<Long, BaseChannel> getChannelCache() {
        return Collections.unmodifiableMap(channelCache);
    }

}