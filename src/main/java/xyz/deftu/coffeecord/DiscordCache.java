package xyz.deftu.coffeecord;

import com.google.gson.JsonObject;
import xyz.deftu.coffeecord.entities.channel.BaseChannel;
import xyz.deftu.coffeecord.entities.channel.GuildChannel;
import xyz.deftu.coffeecord.entities.guild.Guild;
import xyz.deftu.coffeecord.entities.message.Message;
import xyz.deftu.coffeecord.entities.user.User;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class DiscordCache {

    private final DiscordClient client;

    /**
     * Map holding all cached users.
     * Key is the user ID.
     */
    private final Map<Long, User> userCache = new ConcurrentHashMap<>();
    /**
     * Map holding all cached messages.
     * Key is the message ID.
     */
    private final Map<Long, Message> messageCache = new ConcurrentHashMap<>();
    /**
     * Map holding all cached guilds.
     * Key is the guild ID.
     */
    private final Map<Long, Guild> guildCache = new HashMap<>();
    /**
     * Map holding all cached channels.
     * Key is the channel ID.
     */
    private final Map<Long, BaseChannel> channelCache = new HashMap<>();
    /**
     * Map holding all <b>guild</b> cached channels.
     * Key is the guild ID.
     */
    private final Map<Long, List<GuildChannel>> guildChannelCache = new HashMap<>();

    public DiscordCache(DiscordClient client) {
        this.client = client;
    }

    /**
     * Adds a user object to the user cache.
     *
     * @param id The ID of the user being added.
     * @param user The user object to be added.
     */
    public void addUser(long id, User user) {
        userCache.put(id, user);
    }

    /**
     * Adds a user object to the user cache.
     *
     * @param id The ID of the user being added.
     * @param data The user data to be added.
     */
    public void addUser(long id, JsonObject data) {
        addUser(id, client.getObjectCreator().createUser(data));
    }

    /**
     * Gets a user object from the user cache.
     */
    public User getUser(long id) {
        return userCache.get(id);
    }

    /**
     * Adds a message object to the message cache.
     *
     * @param id The ID of the message being added.
     * @param message The message object to be added.
     */
    public void addMessage(long id, Message message) {
        messageCache.put(id, message);
    }

    /**
     * Adds a message object to the message cache.
     *
     * @param id The ID of the message being added.
     * @param data The message data to be added.
     */
    public void addMessage(long id, JsonObject data) {
        addMessage(id, client.getObjectCreator().createMessage(data));
    }

    /**
     * Gets a message object from the message cache.
     */
    public Message getMessage(long id) {
        return messageCache.get(id);
    }

    /**
     * Adds a guild object to the guild cache.
     *
     * @param id The ID of the guild being added.
     * @param guild The guild object to be added.
     */
    public void addGuild(long id, Guild guild) {
        guildCache.put(id, guild);
    }

    /**
     * Adds a guild object to the guild cache.
     *
     * @param id The ID of the guild being added.
     * @param data The guild data to be added.
     */
    public void addGuild(long id, JsonObject data) {
        addGuild(id, client.getObjectCreator().createGuild(data));
    }

    /**
     * Gets a guild object from the guild cache.
     */
    public Guild getGuild(long id) {
        return guildCache.get(id);
    }

    /**
     * Adds a channel object to the channel cache.
     *
     * @param id The ID of the channel being added.
     * @param channel The channel object to be added.
     */
    public void addChannel(long id, BaseChannel channel) {
        channelCache.put(id, channel);
    }

    /**
     * Adds a channel object to the channel cache.
     *
     * @param id The ID of the channel being added.
     * @param data The channel data to be added.
     */
    public void addChannel(long id, JsonObject data) {
        addChannel(id, client.getObjectCreator().createChannel(data));
    }

    /**
     * Gets a channel object from the channel cache.
     */
    public BaseChannel getChannel(long id) {
        return channelCache.get(id);
    }

    /**
     * Adds a guild channel object to the guild channel cache.
     *
     * @param id The ID of the guild channel being added.
     * @param channel The guild channel object to be added.
     */
    public void addGuildChannel(long id, GuildChannel channel) {
        guildChannelCache.putIfAbsent(id, new ArrayList<>());
        guildChannelCache.get(id).add(channel);
    }

    /**
     * Adds a guild channel object to the guild channel cache.
     *
     * @param id The ID of the guild channel being added.
     * @param data The guild channel data to be added.
     */
    public void addGuildChannel(long id, JsonObject data) {
        addGuildChannel(id, (GuildChannel) client.getObjectCreator().createChannel(data));
    }

    /**
     * Gets a guild channel object from the channel cache.
     */
    public List<GuildChannel> getGuildChannels(long id) {
        return guildChannelCache.getOrDefault(id, new ArrayList<>());
    }

    public Map<Long, User> getUserCache() {
        return Collections.unmodifiableMap(userCache);
    }

    public Map<Long, Message> getMessageCache() {
        return Collections.unmodifiableMap(messageCache);
    }

    public Map<Long, Guild> getGuildCache() {
        return Collections.unmodifiableMap(guildCache);
    }

    public Map<Long, BaseChannel> getChannelCache() {
        return Collections.unmodifiableMap(channelCache);
    }

    public Map<Long, List<GuildChannel>> getGuildChannelCache() {
        return Collections.unmodifiableMap(guildChannelCache);
    }

}