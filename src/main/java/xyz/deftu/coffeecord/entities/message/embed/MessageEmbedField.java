package xyz.deftu.coffeecord.entities.message.embed;

import com.google.gson.JsonObject;
import xyz.deftu.coffeecord.entities.JsonSerializable;
import xyz.deftu.deftils.Strings;

public class MessageEmbedField implements JsonSerializable<JsonObject> {

    private final String name;
    private final String value;
    private final boolean inline;

    public MessageEmbedField(String name, String value, boolean inline) {
        this.name = name;
        this.value = value;
        this.inline = inline;
    }

    public MessageEmbedField(String name, String value) {
        this(name, value, false);
    }

    public JsonObject asJson() {
        JsonObject value = new JsonObject();

        if (Strings.isNullOrEmpty(name))
            throw new IllegalStateException("Name of an embed field cannot be null or empty.");
        if (Strings.isNullOrEmpty(this.value))
            throw new IllegalStateException("Value of an embed field cannot be null or empty.");

        value.addProperty("name", name);
        value.addProperty("value", this.value);
        value.addProperty("inline", inline);

        return value;
    }

}