package xyz.deftu.coffeecord.entities.message;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import xyz.deftu.coffeecord.Coffeecord;
import xyz.deftu.coffeecord.DiscordClient;
import xyz.deftu.coffeecord.entities.JsonSerializable;
import xyz.deftu.coffeecord.entities.ISnowflake;
import xyz.deftu.coffeecord.entities.message.embed.MessageEmbed;
import xyz.deftu.coffeecord.entities.user.User;
import xyz.deftu.deftils.Strings;
import xyz.deftu.deftils.exceptions.UnfinishedApiException;

import java.time.OffsetDateTime;
import java.util.List;

public class Message implements ISnowflake, JsonSerializable<JsonObject> {

    private final DiscordClient client;

    private final boolean tts;
    private final OffsetDateTime timestamp;
    private boolean pinned;
    private final long id;
    private final List<MessageEmbed> embeds;
    private final OffsetDateTime editedTimestamp;
    private final String content;
    private MessageReference messageReference;

    private final User author;

    private final long channelId;

    public Message(DiscordClient client, boolean tts, OffsetDateTime timestamp, boolean pinned, long id, List<MessageEmbed> embeds, OffsetDateTime editedTimestamp, String content, MessageReference messageReference, User author, long channelId) {
        this.client = client;
        this.tts = tts;
        this.timestamp = timestamp;
        this.pinned = pinned;
        this.id = id;
        this.embeds = embeds;
        this.editedTimestamp = editedTimestamp;
        this.content = content;
        this.messageReference = messageReference;
        this.author = author;

        this.channelId = channelId;
    }

    public DiscordClient getClient() {
        return client;
    }

    public boolean isTts() {
        return tts;
    }

    public OffsetDateTime getTimestamp() {
        return timestamp;
    }

    public boolean isPinned() {
        return pinned;
    }

    public void pin() {
        throw new UnfinishedApiException();
    }

    public long getId() {
        return id;
    }

    public OffsetDateTime getEditedTimestamp() {
        return editedTimestamp;
    }

    public String getContent() {
        return content;
    }

    public MessageReference getMessageReference() {
        return messageReference;
    }

    public Message withReference(MessageReference messageReference) {
        this.messageReference = messageReference;
        return this;
    }

    public User getAuthor() {
        return author;
    }

    public long getChannelId() {
        return channelId;
    }

    public void reply(Message message, long guildId) {
        message = message.withReference(new MessageReference(getId(), channelId, guildId));
        DiscordClient client = getClient();
        Request request = new Request.Builder()
                .url(Coffeecord.API_URL + "/channels/" + channelId + "/messages")
                .post(RequestBody.create(client.getGson().toJson(message.asJson()), MediaType.get("application/json")))
                .build();
        client.getRequestManager().request(request, true);
    }

    public JsonObject asJson() {
        JsonObject value = new JsonObject();

        boolean tts = isTts();
        String content = getContent();

        value.addProperty("tts", tts);
        if (embeds != null) {
            JsonArray embedsArray = new JsonArray();
            for (MessageEmbed embed : embeds) {
                embedsArray.add(embed.asJson());
            }

            value.add("embeds", embedsArray);
        }

        if (!Strings.isNullOrEmpty(content))
            value.addProperty("content", content);
        if (messageReference != null)
            value.add("message_reference", messageReference.asJson());

        return value;
    }

}