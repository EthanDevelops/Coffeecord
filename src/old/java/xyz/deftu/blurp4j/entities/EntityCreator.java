package xyz.deftu.blurp4j.entities;

import xyz.deftu.blurp4j.DiscordClient;
import xyz.deftu.blurp4j.entities.messages.Message;
import xyz.deftu.blurp4j.entities.messages.MessageEmbed;
import xyz.qalcyo.json.entities.JsonElement;
import xyz.qalcyo.json.entities.JsonObject;

import java.util.ArrayList;
import java.util.List;

public class EntityCreator {

    private final DiscordClient client;

    public EntityCreator(DiscordClient client) {
        this.client = client;
    }

    public Message createMessage(JsonObject data) {
        List<MessageEmbed> embeds = new ArrayList<>();
        for (JsonElement element : data.getAsArray("embeds")) {
            String str = element.getAsString();
            if (str.isEmpty())
                continue;

            JsonObject embed = element.getAsJsonObject();
            embeds.add(MessageEmbed.newBuilder()
                    .title(getOrDefault(embed, "title", null))
                    .description(getOrDefault(embed, "description", null))
                    .colour(getOrDefault(embed, "color", null))
                    .build());
        }
        return Message.newBuilder()
                .content(data.getAsString("content"))
                .tts(data.getAsBoolean("tts"))
                .embeds(embeds)
                .build()
                .setMessageId(data.getAsLong("id"))
                .setGuildId(data.hasKey("guild_id") ? data.getAsLong("guild_id") : -1);
    }

    private <T> T getOrDefault(JsonObject object, String key, T defaultVal) {
        JsonElement gotten = object.get(key);
        return object.hasKey(key) ? gotten.isJsonObject() ? (T) gotten.getAsJsonObject() : gotten.isJsonArray() ? (T) gotten.getAsJsonArray() : (T) gotten.getAsJsonPrimitive().getValue() : defaultVal;
    }

}