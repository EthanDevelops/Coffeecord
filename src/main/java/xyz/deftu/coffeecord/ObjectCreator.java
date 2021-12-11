package xyz.deftu.coffeecord;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import xyz.deftu.coffeecord.commands.ApplicationCommand;
import xyz.deftu.coffeecord.commands.ApplicationCommandType;
import xyz.deftu.coffeecord.commands.impl.MessageCommand;
import xyz.deftu.coffeecord.commands.impl.UserCommand;
import xyz.deftu.coffeecord.commands.impl.slash.SlashCommand;
import xyz.deftu.coffeecord.entities.Permission;
import xyz.deftu.coffeecord.entities.channel.*;
import xyz.deftu.coffeecord.entities.channel.direct.BasePrivateChannel;
import xyz.deftu.coffeecord.entities.channel.direct.PrivateChannel;
import xyz.deftu.coffeecord.entities.channel.direct.PrivateGroupChannel;
import xyz.deftu.coffeecord.entities.channel.guild.GuildCategory;
import xyz.deftu.coffeecord.entities.channel.guild.GuildTextChannel;
import xyz.deftu.coffeecord.entities.guild.Guild;
import xyz.deftu.coffeecord.entities.guild.GuildFeature;
import xyz.deftu.coffeecord.entities.guild.PermissionOverwrite;
import xyz.deftu.coffeecord.entities.guild.PermissionOverwriteType;
import xyz.deftu.coffeecord.entities.message.Message;
import xyz.deftu.coffeecord.entities.message.embed.*;
import xyz.deftu.coffeecord.entities.message.MessageReference;
import xyz.deftu.coffeecord.entities.user.SelfUser;
import xyz.deftu.coffeecord.entities.user.User;
import xyz.deftu.coffeecord.interactions.Interaction;
import xyz.deftu.coffeecord.interactions.InteractionType;
import xyz.deftu.coffeecord.requests.types.ChannelRequest;
import xyz.deftu.coffeecord.requests.types.GuildRequest;
import xyz.deftu.coffeecord.utils.JsonHelper;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

public class ObjectCreator {

    private final DiscordClient client;

    public ObjectCreator(DiscordClient client) {
        this.client = client;
    }

    public SelfUser createSelfUser(JsonObject data) {
        long id = JsonHelper.getLong(data, "id");

        String username = JsonHelper.getString(data, "username");
        String discriminator = JsonHelper.getString(data, "discriminator");
        String avatar = JsonHelper.getString(data, "avatar");
        String banner = JsonHelper.getString(data, "banner");

        int flags = JsonHelper.getInt(data, "flags");

        return new SelfUser(client, id, username, discriminator, avatar, banner, flags);
    }

    public DiscordApplication createDiscordApplication(JsonObject data) {
        long id = JsonHelper.getLong(data, "id");

        return new DiscordApplication(id);
    }

    public ApplicationCommand createApplicationCommand(JsonObject data) {
        ApplicationCommandType type = ApplicationCommandType.from(JsonHelper.getInt(data, "type"));
        return type == ApplicationCommandType.CHAT ?
                createSlashCommand(data) :
                (type == ApplicationCommandType.USER ?
                        createUserCommand(data) :
                        (type == ApplicationCommandType.MESSAGE ?
                                createMessageCommand(data) :
                                null));
    }

    public SlashCommand createSlashCommand(JsonObject data) {
        long id = JsonHelper.getLong(data, "id");
        long guildId = JsonHelper.getLong(data, "guild_id");
        String name = JsonHelper.getString(data, "name");
        String description = JsonHelper.getString(data, "description");
        boolean defaultPermission = JsonHelper.getBoolean(data, "default_permission");

        return new SlashCommand(id, guildId, name, description, defaultPermission);
    }

    public UserCommand createUserCommand(JsonObject data) {
        return null; // TODO
    }

    public MessageCommand createMessageCommand(JsonObject data) {
        return null; // TODO
    }

    public Interaction createInteraction(JsonObject data) {
        long id = JsonHelper.getLong(data, "id");

        long applicationId = JsonHelper.getLong(data, "application_id");
        DiscordApplication application = client.getApplication();

        long version = JsonHelper.getLong(data, "version");
        InteractionType type = InteractionType.from(JsonHelper.getInt(data, "type"));

        long guildId = JsonHelper.getLong(data, "guild_id");
        Guild guild = null;
        if (guildId != -1) {
            Guild cached = guild = client.getDiscordCache().getGuild(guildId);
            if (cached == null) {
                Guild fetched = guild = client.getRestRequester().request(new GuildRequest(client, guildId));
                client.getDiscordCache().addGuild(fetched.getId(), fetched);
            }
        }

        long channelId = JsonHelper.getLong(data, "channel_id");
        BaseChannel channel = null;
        if (channelId != -1) {
            BaseChannel cached = channel = client.getDiscordCache().getChannel(channelId);
            if (cached == null) {
                BaseChannel fetched = channel = client.getRestRequester().request(new ChannelRequest(client, channelId));
                client.getDiscordCache().addChannel(fetched.getId(), fetched);
            }
        }

        JsonObject userRaw = JsonHelper.getObject(data, "user");
        User user = null;
        if (userRaw != null) {
            user = createUser(userRaw);
        }

        String token = JsonHelper.getString(data, "token");

        JsonObject messageRaw = JsonHelper.getObject(data, "message");
        Message message = null;
        if (messageRaw != null) {
            message = createMessage(messageRaw);
        }

        return new Interaction(client, id, applicationId == application.getId() ? application : null, version, type, guild, channel, user, token, message);
    }

    public User createUser(JsonObject data) {
        long id = JsonHelper.getLong(data, "id");

        String username = JsonHelper.getString(data, "username");
        String discriminator = JsonHelper.getString(data, "discriminator");
        String avatar = JsonHelper.getString(data, "avatar");
        boolean bot = JsonHelper.getBoolean(data, "bot");
        boolean system = JsonHelper.getBoolean(data, "system");
        String banner = JsonHelper.getString(data, "banner");

        int flags = JsonHelper.getInt(data, "flags");

        return new User(client, id, username, discriminator, avatar, bot, system, banner, flags);
    }

    public Message createMessage(JsonObject data) {
        boolean tts = JsonHelper.getBoolean(data, "tts");

        String timestampRaw = JsonHelper.getString(data, "timestamp");
        OffsetDateTime timestamp = timestampRaw == null ? OffsetDateTime.now() : OffsetDateTime.parse(timestampRaw);

        // TODO - Referenced message.

        boolean pinned = JsonHelper.getBoolean(data, "pinned");

        // TODO - Mentions
        // TODO - Mentioned roles
        // TODO - Mentions everyone
        // TODO - Member

        long id = JsonHelper.getLong(data, "id");

        // TODO - Flags

        List<MessageEmbed> embeds = new ArrayList<>();
        if (data.has("embeds")) {
            JsonElement embedsElement = data.get("embeds");
            if (embedsElement.isJsonArray()) {
                JsonArray embedsArray = embedsElement.getAsJsonArray();
                for (JsonElement element : embedsArray) {
                    if (element.isJsonObject()) {
                        embeds.add(createMessageEmbed(element.getAsJsonObject()));
                    }
                }
            }
        }

        String editedTimestampRaw = JsonHelper.getString(data, "edited_timestamp");
        OffsetDateTime editedTimestamp = editedTimestampRaw == null ? null : OffsetDateTime.parse(editedTimestampRaw);

        String content = JsonHelper.getString(data, "content");

        // TODO - Components

        long channelId = JsonHelper.getLong(data, "channel_id");

        MessageReference messageReference = null;
        if (data.has("message_reference")) {
            JsonElement messageReferenceElement = data.get("message_reference");
            if (messageReferenceElement.isJsonObject()) {
                JsonObject messageReferenceObject = messageReferenceElement.getAsJsonObject();

                long referencedMessageId = -1;
                if (messageReferenceObject.has("message_id")) {
                    JsonElement messageIdElement = messageReferenceObject.get("message_id");
                    if (messageIdElement.isJsonObject()) {
                        JsonPrimitive messageIdPrimitive = messageIdElement.getAsJsonPrimitive();
                        if (messageIdPrimitive.isNumber()) {
                            referencedMessageId = messageIdPrimitive.getAsLong();
                        }
                    }
                }

                long referencedChannelId = -1;
                if (messageReferenceObject.has("channel_id")) {
                    JsonElement channelIdElement = messageReferenceObject.get("channel_id");
                    if (channelIdElement.isJsonObject()) {
                        JsonPrimitive channelIdPrimitive = channelIdElement.getAsJsonPrimitive();
                        if (channelIdPrimitive.isNumber()) {
                            referencedChannelId = channelIdPrimitive.getAsLong();
                        }
                    }
                }

                long referencedGuildId = -1;
                if (messageReferenceObject.has("guild_id")) {
                    JsonElement guildIdObject = messageReferenceObject.get("guild_id");
                    if (guildIdObject.isJsonObject()) {
                        JsonPrimitive guildIdPrimitive = guildIdObject.getAsJsonPrimitive();
                        if (guildIdPrimitive.isNumber()) {
                            referencedGuildId = guildIdPrimitive.getAsLong();
                        }
                    }
                }

                messageReference = new MessageReference(referencedMessageId, referencedChannelId, referencedGuildId);
            }
        }

        JsonObject authorRaw = JsonHelper.getObject(data, "author");
        User author = authorRaw == null ? null : createUser(authorRaw);

        // TODO - Attachments

        BaseChannel channel = null;
        if (channelId != -1) {
            BaseChannel cached = channel = client.getDiscordCache().getChannel(channelId);
            if (cached == null) {
                BaseChannel fetched = channel = client.getRestRequester().request(new ChannelRequest(client, channelId));
                client.getDiscordCache().addChannel(fetched.getId(), fetched);
            }
        }

        return new Message(client, tts, timestamp, pinned, id, embeds, editedTimestamp, content, messageReference, author, channel == null ? new LimitedChannel(client, channelId) : channel);
    }

    public MessageEmbed createMessageEmbed(JsonObject data) {
        String title = JsonHelper.getString(data, "title");

        String typeRaw = JsonHelper.getString(data, "type");
        MessageEmbedType type = typeRaw == null ? MessageEmbedType.RICH : MessageEmbedType.from(typeRaw);

        String description = JsonHelper.getString(data, "description");
        String url = JsonHelper.getString(data, "url");

        String timestampRaw = JsonHelper.getString(data, "timestamp");
        OffsetDateTime timestamp = timestampRaw == null ? null : OffsetDateTime.parse(timestampRaw);

        int colour = JsonHelper.getInt(data, "color");

        JsonObject footerRaw = JsonHelper.getObject(data, "footer");
        MessageEmbedFooter footer = footerRaw == null ? null : createMessageEmbedFooter(footerRaw);

        JsonObject imageRaw = JsonHelper.getObject(data, "image");
        MessageEmbedMedia image = imageRaw == null ? null : createMessageEmbedMedia(imageRaw);

        JsonObject thumbnailRaw = JsonHelper.getObject(data, "thumbnail");
        MessageEmbedMedia thumbnail = thumbnailRaw == null ? null : createMessageEmbedMedia(thumbnailRaw);

        JsonObject videoRaw = JsonHelper.getObject(data, "video");
        MessageEmbedMedia video = videoRaw == null ? null : createMessageEmbedMedia(videoRaw);

        JsonObject authorRaw = JsonHelper.getObject(data, "author");
        MessageEmbedAuthor author = authorRaw == null ? null : createMessageEmbedAuthor(authorRaw);

        List<MessageEmbedField> fields = new ArrayList<>();
        if (data.has("fields")) {
            JsonElement fieldsElement = data.get("fields");
            if (fieldsElement.isJsonArray()) {
                JsonArray fieldsArray = fieldsElement.getAsJsonArray();
                for (JsonElement element : fieldsArray) {
                    if (element.isJsonObject()) {
                        fields.add(createMessageEmbedField(element.getAsJsonObject()));
                    }
                }
            }
        }

        return new MessageEmbed(title, type, description, url, timestamp, colour, footer, image, thumbnail, video, author, fields);
    }

    public MessageEmbedFooter createMessageEmbedFooter(JsonObject data) {
        String text = JsonHelper.getString(data, "text");
        String icon = JsonHelper.getString(data, "icon_url");
        String proxyIcon = JsonHelper.getString(data, "proxy_icon_url");

        return new MessageEmbedFooter(text, icon, proxyIcon);
    }

    public MessageEmbedMedia createMessageEmbedMedia(JsonObject data) {
        String url = JsonHelper.getString(data, "url");
        String proxyUrl = JsonHelper.getString(data, "proxy_url");
        int width = JsonHelper.getInt(data, "width");
        int height = JsonHelper.getInt(data, "height");

        return new MessageEmbedMedia(url, proxyUrl, width, height);
    }

    public MessageEmbedAuthor createMessageEmbedAuthor(JsonObject data) {
        String name = JsonHelper.getString(data, "name");
        String url = JsonHelper.getString(data, "url");
        String iconUrl = JsonHelper.getString(data, "icon_url");
        String proxyIconUrl = JsonHelper.getString(data, "proxy_icon_url");

        return new MessageEmbedAuthor(name, url, iconUrl, proxyIconUrl);
    }

    public MessageEmbedField createMessageEmbedField(JsonObject data) {
        String name = JsonHelper.getString(data, "name");
        String value = JsonHelper.getString(data, "value");
        boolean inline = JsonHelper.getBoolean(data, "inline");

        return new MessageEmbedField(name, value, inline);
    }

    public BaseChannel createChannel(JsonObject data) {
        long id = JsonHelper.getLong(data, "id");
        ChannelType type = ChannelType.from(JsonHelper.getInt(data, "type"));

        if (type == ChannelType.UNKNOWN) {
            throw new IllegalStateException("Couldn't determine type of a channel.");
        }

        return type.isGuild() ? createGuildChannel(data, id, type) : (type.isDirect() ? createPrivateChannel(data, id, type) : createLimitedChannel(id));
    }

    public GuildChannel createGuildChannel(JsonObject data, long id, ChannelType type) {
        long guildId = JsonHelper.getLong(data, "guild_id");
        Guild guild = null;
        if (guildId != -1) {
            Guild cached = guild = client.getDiscordCache().getGuild(guildId);
            if (cached == null) {
                Guild fetched = guild = client.getRestRequester().request(new GuildRequest(client, guildId));
                client.getDiscordCache().addGuild(fetched.getId(), fetched);
            }
        }

        return type == ChannelType.GUILD_TEXT ?
                createGuildTextChannel(data, id, guild) :
                (type == ChannelType.GUILD_CATEGORY ?
                        createGuildCategory(data, id, guild) :
                        null);
    }

    public GuildTextChannel createGuildTextChannel(JsonObject data, long id, Guild guild) {
        String name = JsonHelper.getString(data, "name");
        int position = JsonHelper.getInt(data, "position");

        JsonArray permissionOverwritesRaw = JsonHelper.getArray(data, "permission_overwrites");
        List<PermissionOverwrite> permissionOverwrites = new ArrayList<>();
        if (permissionOverwritesRaw != null) {
            for (JsonElement overwrite : permissionOverwritesRaw) {
                if (overwrite.isJsonObject()) {
                    permissionOverwrites.add(createPermissionOverwrite(overwrite.getAsJsonObject()));
                }
            }
        }

        long parentId = JsonHelper.getLong(data, "parent_id");
        int cooldown = JsonHelper.getInt(data, "rate_limit_per_user");
        boolean nsfw = JsonHelper.getBoolean(data, "nsfw");
        String topic = JsonHelper.getString(data, "topic");

        return new GuildTextChannel(client, id, guild, name, position, permissionOverwrites, parentId, cooldown, nsfw, topic);
    }

    public GuildCategory createGuildCategory(JsonObject data, long id, Guild guild) {
        String name = JsonHelper.getString(data, "name");
        int position = JsonHelper.getInt(data, "position");

        JsonArray permissionOverwritesRaw = JsonHelper.getArray(data, "permission_overwrites");
        List<PermissionOverwrite> permissionOverwrites = new ArrayList<>();
        if (permissionOverwritesRaw != null) {
            for (JsonElement overwrite : permissionOverwritesRaw) {
                if (overwrite.isJsonObject()) {
                    permissionOverwrites.add(createPermissionOverwrite(overwrite.getAsJsonObject()));
                }
            }
        }

        return new GuildCategory(client, id, guild, name, position, permissionOverwrites);
    }

    public BasePrivateChannel createPrivateChannel(JsonObject data, long id, ChannelType type) {
        long lastMessageId = JsonHelper.getLong(data, "last_message_id");

        JsonArray recipientsRaw = JsonHelper.getArray(data, "recipients");
        List<User> recipients = new ArrayList<>();
        if (recipientsRaw != null) {
            for (JsonElement recipient : recipientsRaw) {
                if (recipient.isJsonObject()) {
                    recipients.add(createUser(recipient.getAsJsonObject()));
                }
            }
        }

        return type == ChannelType.DIRECT ? new PrivateChannel(client, id, lastMessageId, recipients.size() == 1 ? recipients.get(0) : null) : createPrivateGroupChannel(data, id, lastMessageId, recipients);
    }

    public PrivateGroupChannel createPrivateGroupChannel(JsonObject data, long id, long lastMessageId, List<User> recipients) {
        String name = JsonHelper.getString(data, "name");
        String icon = JsonHelper.getString(data, "icon");
        long ownerId = JsonHelper.getLong(data, "owner_id");

        return new PrivateGroupChannel(client, id, lastMessageId, name, icon, recipients, ownerId);
    }

    public LimitedChannel createLimitedChannel(long id) {
        return new LimitedChannel(client, id);
    }

    public PermissionOverwrite createPermissionOverwrite(JsonObject data) {
        long id = JsonHelper.getLong(data, "id");
        int typeRaw = JsonHelper.getInt(data, "type");
        PermissionOverwriteType type = PermissionOverwriteType.from(typeRaw);
        long allowRaw = JsonHelper.getLong(data, "allow");
        List<Permission> allow = allowRaw == -1 ? new ArrayList<>() : new ArrayList<>(Permission.from(allowRaw));
        long denyRaw = JsonHelper.getLong(data, "deny");
        List<Permission> deny = denyRaw == -1 ? new ArrayList<>() : new ArrayList<>(Permission.from(denyRaw));

        return new PermissionOverwrite(id, type, allow, deny);
    }

    public Guild createGuild(JsonObject data) {
        long id = JsonHelper.getLong(data, "id");

        String name = JsonHelper.getString(data, "name");
        String icon = JsonHelper.getString(data, "icon");
        boolean owner = JsonHelper.getBoolean(data, "owner");

        List<Permission> permissions = new ArrayList<>(Permission.from(JsonHelper.getInt(data, "permissions")));

        JsonArray featuresRaw = JsonHelper.getArray(data, "features");
        List<GuildFeature> features = new ArrayList<>();
        if (featuresRaw != null) {
            for (JsonElement feature : featuresRaw) {
                if (feature.isJsonPrimitive()) {
                    JsonPrimitive primitive = feature.getAsJsonPrimitive();
                    if (primitive.isString()) {
                        try {
                            features.add(GuildFeature.valueOf(primitive.getAsString()));
                        } catch (Exception ignored) {
                        }
                    }
                }
            }
        }

        return new Guild(client, id, name, icon, owner, permissions, features);
    }

}