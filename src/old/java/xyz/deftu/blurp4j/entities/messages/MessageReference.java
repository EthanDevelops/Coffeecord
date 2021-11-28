package xyz.deftu.blurp4j.entities.messages;

import xyz.deftu.blurp4j.interfaces.IJsonifiable;
import xyz.qalcyo.json.entities.JsonObject;

public class MessageReference implements IJsonifiable<JsonObject> {

    private final long messageId, guildId;
    private final boolean failIfNotExists;

    public MessageReference(long messageId, long guildId, boolean failIfNotExists) {
        this.messageId = messageId;
        this.guildId = guildId;
        this.failIfNotExists = failIfNotExists;
    }

    public long getMessageId() {
        return messageId;
    }

    public long getGuildId() {
        return guildId;
    }

    public boolean shouldFailIfDoesntExist() {
        return failIfNotExists;
    }

    public JsonObject jsonify() {
        JsonObject value = new JsonObject();
        value.add("message_id", messageId);
        value.add("guild_id", guildId);
        value.add("fail_if_not_exists", failIfNotExists);
        return value;
    }

}