package xyz.deftu.coffeecord.entities;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import xyz.deftu.coffeecord.DiscordClient;
import xyz.deftu.coffeecord.entities.message.Message;
import xyz.deftu.coffeecord.entities.message.MessageReference;
import xyz.deftu.coffeecord.entities.user.User;

import java.time.OffsetDateTime;

public class EntityCreator {

    private final DiscordClient client;

    public EntityCreator(DiscordClient client) {
        this.client = client;
    }

    public User createUser(JsonObject data) {
        long id = -1;
        if (data.has("id")) {
            JsonElement idElement = data.get("id");
            if (idElement.isJsonPrimitive()) {
                JsonPrimitive idPrimitive = idElement.getAsJsonPrimitive();
                if (idPrimitive.isString() || idPrimitive.isNumber()) {
                    id = idPrimitive.getAsLong();
                }
            }
        }

        String username = null;
        if (data.has("username")) {
            JsonElement usernameElement = data.get("username");
            if (usernameElement.isJsonPrimitive()) {
                JsonPrimitive usernamePrimitive = usernameElement.getAsJsonPrimitive();
                if (usernamePrimitive.isString()) {
                    username = usernamePrimitive.getAsString();
                }
            }
        }

        String discriminator = null;
        if (data.has("discriminator")) {
            JsonElement discriminatorElement = data.get("discriminator");
            if (discriminatorElement.isJsonPrimitive()) {
                JsonPrimitive discriminatorPrimitive = discriminatorElement.getAsJsonPrimitive();
                if (discriminatorPrimitive.isString() || discriminatorPrimitive.isNumber()) {
                    discriminator = discriminatorPrimitive.getAsString();
                }
            }
        }

        String avatar = null;
        if (data.has("avatar")) {
            JsonElement avatarElement = data.get("avatar");
            if (avatarElement.isJsonPrimitive()) {
                JsonPrimitive avatarPrimitive = avatarElement.getAsJsonPrimitive();
                if (avatarPrimitive.isString()) {
                    avatar = avatarPrimitive.getAsString();
                }
            }
        }

        boolean bot = false;
        if (data.has("bot")) {
            JsonElement botElement = data.get("bot");
            if (botElement.isJsonPrimitive()) {
                JsonPrimitive botPrimitive = botElement.getAsJsonPrimitive();
                if (botPrimitive.isBoolean()) {
                    bot = botPrimitive.getAsBoolean();
                }
            }
        }

        boolean system = false;
        if (data.has("system")) {
            JsonElement systemElement = data.get("system");
            if (systemElement.isJsonPrimitive()) {
                JsonPrimitive systemPrimitive = systemElement.getAsJsonPrimitive();
                if (systemPrimitive.isBoolean()) {
                    system = systemPrimitive.getAsBoolean();
                }
            }
        }

        String banner = null;
        if (data.has("banner")) {
            JsonElement bannerElement = data.get("banner");
            if (bannerElement.isJsonPrimitive()) {
                JsonPrimitive bannerPrimitive = bannerElement.getAsJsonPrimitive();
                if (bannerPrimitive.isString()) {
                    banner = bannerPrimitive.getAsString();
                }
            }
        }

        int flags = -1;
        if (data.has("public_flags")) {
            JsonElement flagsElement = data.get("public_flags");
            if (flagsElement.isJsonPrimitive()) {
                JsonPrimitive flagsPrimitive = flagsElement.getAsJsonPrimitive();
                if (flagsPrimitive.isNumber()) {
                    flags = flagsPrimitive.getAsInt();
                }
            }
        }

        return new User(client, id, username, discriminator, avatar, bot, system, banner, flags);
    }

    public Message createMessage(JsonObject data) {
        boolean tts = false;
        if (data.has("tts")) {
            JsonElement ttsElement = data.get("tts");
            if (ttsElement.isJsonPrimitive()) {
                JsonPrimitive ttsPrimitive = ttsElement.getAsJsonPrimitive();
                if (ttsPrimitive.isBoolean()) {
                    tts = ttsPrimitive.getAsBoolean();
                }
            }
        }

        OffsetDateTime timestamp = OffsetDateTime.now();
        if (data.has("timestamp")) {
            JsonElement timestampElement = data.get("timestamp");
            if (timestampElement.isJsonPrimitive()) {
                JsonPrimitive timestampPrimitive = timestampElement.getAsJsonPrimitive();
                if (timestampPrimitive.isString()) {
                    timestamp = OffsetDateTime.parse(timestampPrimitive.getAsString());
                }
            }
        }

        // TODO - Referenced message.

        boolean pinned = false;
        if (data.has("pinned")) {
            JsonElement pinnedElement = data.get("pinned");
            if (pinnedElement.isJsonPrimitive()) {
                JsonPrimitive pinnedPrimitive = pinnedElement.getAsJsonPrimitive();
                if (pinnedPrimitive.isBoolean()) {
                    pinned = pinnedPrimitive.getAsBoolean();
                }
            }
        }

        // TODO - Mentions
        // TODO - Mentioned roles
        // TODO - Mentions everyone
        // TODO - Member

        long id = -1;
        if (data.has("id")) {
            JsonElement idElement = data.get("id");
            if (idElement.isJsonPrimitive()) {
                JsonPrimitive idPrimitive = idElement.getAsJsonPrimitive();
                if (idPrimitive.isNumber() || idPrimitive.isString()) {
                    id = idPrimitive.getAsLong();
                }
            }
        }

        // TODO - Flags
        // TODO - Embeds

        OffsetDateTime editedTimestamp = null;
        if (data.has("edited_timestamp")) {
            JsonElement editedTimestampElement = data.get("edited_timestamp");
            if (editedTimestampElement.isJsonPrimitive()) {
                JsonPrimitive editedTimestampPrimitive = editedTimestampElement.getAsJsonPrimitive();
                if (editedTimestampPrimitive.isString()) {
                    editedTimestamp = OffsetDateTime.parse(editedTimestampPrimitive.getAsString());
                }
            }
        }

        String content = null;
        if (data.has("content")) {
            JsonElement contentElement = data.get("content");
            if (contentElement.isJsonPrimitive()) {
                JsonPrimitive contentPrimitive = contentElement.getAsJsonPrimitive();
                if (contentPrimitive.isString()) {
                    content = contentPrimitive.getAsString();
                }
            }
        }

        // TODO - Components

        long channelId = -1;
        if (data.has("channel_id")) {
            JsonElement channelIdElement = data.get("channel_id");
            if (channelIdElement.isJsonPrimitive()) {
                JsonPrimitive channelIdPrimitive = channelIdElement.getAsJsonPrimitive();
                if (channelIdPrimitive.isNumber() || channelIdPrimitive.isString()) {
                    channelId = channelIdPrimitive.getAsLong();
                }
            }
        }

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

        User author = null;
        if (data.has("author")) {
            JsonElement authorElement = data.get("author");
            if (authorElement.isJsonObject()) {
                author = createUser(authorElement.getAsJsonObject());
            }
        }

        // TODO - Attachments

        return new Message(client, tts, timestamp, pinned, id, editedTimestamp, content, messageReference, author, channelId);
    }

}