package xyz.deftu.coffeecord.entities.message;

import com.google.gson.JsonObject;
import xyz.deftu.coffeecord.entities.JsonSerializable;

public class MessageReference implements JsonSerializable<JsonObject> {

    private final long messageId;
    private final long channelId;
    private final long guildId;

    public MessageReference(long messageId, long channelId, long guildId) {
        this.messageId = messageId;
        this.channelId = channelId;
        this.guildId = guildId;
    }

    public long getMessageId() {
        return messageId;
    }

    public long getChannelId() {
        return channelId;
    }

    public long getGuildId() {
        return guildId;
    }

    public JsonObject asJson() {
        JsonObject value = new JsonObject();

        if (messageId != -1)
            value.addProperty("message_id", messageId);
        if (channelId != -1)
            value.addProperty("channel_id", channelId);
        if (guildId != -1)
            value.addProperty("guild_id", guildId);
        value.addProperty("fail_if_not_exists", true);

        return value;
    }

}