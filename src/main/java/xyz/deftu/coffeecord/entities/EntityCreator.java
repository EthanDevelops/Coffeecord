package xyz.deftu.coffeecord.entities;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import xyz.deftu.coffeecord.DiscordClient;
import xyz.deftu.coffeecord.entities.message.Message;
import xyz.deftu.coffeecord.entities.message.embed.*;
import xyz.deftu.coffeecord.entities.message.MessageReference;
import xyz.deftu.coffeecord.entities.user.User;
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

        return new Message(client, tts, timestamp, pinned, id, embeds, editedTimestamp, content, messageReference, author, channelId);
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

}