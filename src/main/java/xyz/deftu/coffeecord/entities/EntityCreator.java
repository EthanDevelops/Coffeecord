package xyz.deftu.coffeecord.entities;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import xyz.deftu.coffeecord.DiscordClient;
import xyz.deftu.coffeecord.entities.channel.*;
import xyz.deftu.coffeecord.entities.channel.direct.BasePrivateChannel;
import xyz.deftu.coffeecord.entities.channel.direct.PrivateChannel;
import xyz.deftu.coffeecord.entities.channel.direct.PrivateGroupChannel;
import xyz.deftu.coffeecord.entities.channel.guild.GuildTextChannel;
import xyz.deftu.coffeecord.entities.guild.Guild;
import xyz.deftu.coffeecord.entities.guild.GuildFeature;
import xyz.deftu.coffeecord.entities.guild.GuildPermission;
import xyz.deftu.coffeecord.entities.message.Message;
import xyz.deftu.coffeecord.entities.message.embed.*;
import xyz.deftu.coffeecord.entities.message.MessageReference;
import xyz.deftu.coffeecord.entities.user.User;
import xyz.deftu.coffeecord.requests.types.ChannelRequest;
import xyz.deftu.coffeecord.requests.types.GuildRequest;
import xyz.deftu.coffeecord.utils.JsonHelper;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

public class EntityCreator {

    private final DiscordClient client;

    public EntityCreator(DiscordClient client) {
        this.client = client;
    }

    public User createUser(JsonObject data) {
        Number idRaw = JsonHelper.getNumber(data, "id");
        long id = idRaw == null ? -1 : idRaw.longValue();

        String username = JsonHelper.getString(data, "username");
        String discriminator = JsonHelper.getString(data, "discriminator");
        String avatar = JsonHelper.getString(data, "avatar");
        boolean bot = JsonHelper.getBoolean(data, "bot");
        boolean system = JsonHelper.getBoolean(data, "system");
        String banner = JsonHelper.getString(data, "banner");

        Number flagsRaw = JsonHelper.getNumber(data, "flags");
        int flags = flagsRaw == null ? -1 : flagsRaw.intValue();

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

        Number idRaw = JsonHelper.getNumber(data, "id");
        long id = idRaw == null ? -1 : idRaw.longValue();

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

        Number channelIdRaw = JsonHelper.getNumber(data, "channel_id");
        long channelId = channelIdRaw == null ? -1 : channelIdRaw.longValue();

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

        BaseChannel channel = channelId == -1 ? null : client.getRestRequester().request(new ChannelRequest(client, channelId));

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

        Number colourRaw = JsonHelper.getNumber(data, "color");
        int colour = colourRaw == null ? 0 : colourRaw.intValue();

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

        Number widthRaw = JsonHelper.getNumber(data, "width");
        int width = widthRaw == null ? -1 : widthRaw.intValue();

        Number heightRaw = JsonHelper.getNumber(data, "height");
        int height = heightRaw == null ? -1 : heightRaw.intValue();

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
        Number idRaw = JsonHelper.getNumber(data, "id");
        long id = idRaw == null ? -1 : idRaw.longValue();

        Number typeRaw = JsonHelper.getNumber(data, "type");
        ChannelType type = typeRaw == null ? ChannelType.UNKNOWN : ChannelType.from(typeRaw.intValue());

        if (type == ChannelType.UNKNOWN) {
            throw new IllegalStateException("Couldn't determine type of a channel.");
        }

        return type.isGuild() ? createGuildChannel(data, id, type) : (type.isDirect() ? createPrivateChannel(data, id, type) : createLimitedChannel(id));
    }

    public GuildChannel createGuildChannel(JsonObject data, long id, ChannelType type) {
        Number guildIdRaw = JsonHelper.getNumber(data, "guild_id");
        Guild guild = guildIdRaw == null ? null : client.getRestRequester().request(new GuildRequest(client, guildIdRaw.longValue()));
        return type == ChannelType.GUILD_TEXT ?
                createGuildTextChannel(data, id, guild) :
                null;
    }

    public GuildTextChannel createGuildTextChannel(JsonObject data, long id, Guild guild) {
        String name = JsonHelper.getString(data, "name");

        Number positionRaw = JsonHelper.getNumber(data, "position");
        int position = positionRaw == null ? -1 : positionRaw.intValue();

        JsonArray permissionOverwritesRaw = JsonHelper.getArray(data, "permission_overwrites");
        List<GuildPermission> permissionOverwrites = new ArrayList<>();
        if (permissionOverwritesRaw != null) {
            for (JsonElement overwrite : permissionOverwritesRaw) {
                System.out.println(overwrite);
            }
        }

        Number parentIdRaw = JsonHelper.getNumber(data, "parent_id");
        long parentId = parentIdRaw == null ? -1 : parentIdRaw.longValue();

        Number rateLimitPerUserRaw = JsonHelper.getNumber(data, "rate_limit_per_user");
        int cooldown = rateLimitPerUserRaw == null ? -1 : rateLimitPerUserRaw.intValue();

        boolean nsfw = JsonHelper.getBoolean(data, "nsfw");
        String topic = JsonHelper.getString(data, "topic");

        return new GuildTextChannel(client, id, guild, name, position, permissionOverwrites, parentId, cooldown, nsfw, topic);
    }

    public BasePrivateChannel createPrivateChannel(JsonObject data, long id, ChannelType type) {
        Number lastMessageIdRaw = JsonHelper.getNumber(data, "last_message_id");
        long lastMessageId = lastMessageIdRaw == null ? -1 : lastMessageIdRaw.longValue();

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

        Number ownerIdRaw = JsonHelper.getNumber(data, "owner_id");
        long ownerId = ownerIdRaw == null ? -1 : ownerIdRaw.longValue();

        return new PrivateGroupChannel(client, id, lastMessageId, name, icon, recipients, ownerId);
    }

    public LimitedChannel createLimitedChannel(long id) {
        return new LimitedChannel(client, id);
    }

    public Guild createGuild(JsonObject data) {
        Number idRaw = JsonHelper.getNumber(data, "id");
        long id = idRaw == null ? -1 : idRaw.longValue();

        String name = JsonHelper.getString(data, "name");
        String icon = JsonHelper.getString(data, "icon");
        boolean owner = JsonHelper.getBoolean(data, "owner");

        Number permissionsRaw = JsonHelper.getNumber(data, "permissions");
        List<GuildPermission> permissions = permissionsRaw == null ? new ArrayList<>() : new ArrayList<>(GuildPermission.from(permissionsRaw.longValue()));

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