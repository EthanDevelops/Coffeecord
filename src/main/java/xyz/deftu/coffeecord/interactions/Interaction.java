package xyz.deftu.coffeecord.interactions;

import xyz.deftu.coffeecord.DiscordApplication;
import xyz.deftu.coffeecord.DiscordClient;
import xyz.deftu.coffeecord.entities.ISnowflake;
import xyz.deftu.coffeecord.entities.channel.BaseChannel;
import xyz.deftu.coffeecord.entities.guild.Guild;
import xyz.deftu.coffeecord.entities.message.Message;
import xyz.deftu.coffeecord.entities.user.User;
import xyz.deftu.coffeecord.requests.types.interactions.InteractionRespondRequest;

public class Interaction implements ISnowflake {

    private final DiscordClient client;

    private final long id;
    private final DiscordApplication application;
    private final long version;
    private final InteractionType type;
    private final Guild guild;
    private final BaseChannel channel;
    // TODO - member
    private final User user;
    private final String token;
    private final Message message;

    public Interaction(DiscordClient client, long id, DiscordApplication application, long version, InteractionType type, Guild guild, BaseChannel channel, User user, String token, Message message) {
        this.client = client;
        this.id = id;
        this.application = application;
        this.version = version;
        this.type = type;
        this.guild = guild;
        this.channel = channel;
        this.user = user;
        this.token = token;
        this.message = message;
    }

    public DiscordClient getClient() {
        return client;
    }

    public long getId() {
        return id;
    }

    public DiscordApplication getApplication() {
        return application;
    }

    public long getVersion() {
        return version;
    }

    public InteractionType getType() {
        return type;
    }

    public Guild getGuild() {
        return guild;
    }

    public BaseChannel getChannel() {
        return channel;
    }

    public User getUser() {
        return user;
    }

    public String getToken() {
        return token;
    }

    public Message getMessage() {
        return message;
    }

}