package xyz.deftu.coffeecord.entities.message.embed;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import xyz.deftu.coffeecord.entities.JsonSerializable;
import xyz.deftu.deftils.Strings;

import java.awt.*;
import java.time.OffsetDateTime;
import java.util.List;

public class MessageEmbed implements JsonSerializable<JsonObject> {

    private final String title;
    private final MessageEmbedType type;
    private final String description;
    private final String url;
    private final OffsetDateTime timestamp;
    private final int colour;
    private final MessageEmbedFooter footer;
    private final MessageEmbedMedia image;
    private final MessageEmbedMedia thumbnail;
    private final MessageEmbedMedia video;
    private final MessageEmbedAuthor author;
    private final List<MessageEmbedField> fields;

    public MessageEmbed(String title, MessageEmbedType type, String description, String url, OffsetDateTime timestamp, int colour, MessageEmbedFooter footer, MessageEmbedMedia image, MessageEmbedMedia thumbnail, MessageEmbedMedia video, MessageEmbedAuthor author, List<MessageEmbedField> fields) {
        this.title = title;
        this.type = type;
        this.description = description;
        this.url = url;
        this.timestamp = timestamp;
        this.colour = colour;
        this.footer = footer;
        this.image = image;
        this.thumbnail = thumbnail;
        this.video = video;
        this.author = author;
        this.fields = fields;
    }

    public String getTitle() {
        return title;
    }

    public MessageEmbedType getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }

    public OffsetDateTime getTimestamp() {
        return timestamp;
    }

    public Color getColour() {
        return new Color(colour);
    }

    public int getColourRaw() {
        return colour;
    }

    public MessageEmbedFooter getFooter() {
        return footer;
    }

    public MessageEmbedMedia getImage() {
        return image;
    }

    public MessageEmbedMedia getThumbnail() {
        return thumbnail;
    }

    public MessageEmbedMedia getVideo() {
        return video;
    }

    public MessageEmbedAuthor getAuthor() {
        return author;
    }

    public List<MessageEmbedField> getFields() {
        return fields;
    }

    public JsonObject asJson() {
        JsonObject value = new JsonObject();

        if (type == null)
            throw new IllegalStateException("Type cannot be null.");

        if (!Strings.isNullOrEmpty(title))
            value.addProperty("title", title);
        if (!Strings.isNullOrEmpty(description))
            value.addProperty("description", description);
        if (!Strings.isNullOrEmpty(url))
            value.addProperty("url", url);
        if (timestamp != null)
            value.addProperty("timestamp", timestamp.toString());
        value.addProperty("color", colour & 0xFFFFFF);
        if (footer != null)
            value.add("footer", footer.asJson());
        if (image != null)
            value.add("image", image.asJson());
        if (thumbnail != null)
            value.add("thumbnail", thumbnail.asJson());
        if (video != null)
            value.add("video", video.asJson());
        if (author != null)
            value.add("author", author.asJson());
        if (fields != null) {
            JsonArray fieldsArray = new JsonArray();
            for (MessageEmbedField field : fields)
                fieldsArray.add(field.asJson());
            value.add("fields", fieldsArray);
        }

        return value;
    }

}