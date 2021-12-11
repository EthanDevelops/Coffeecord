package xyz.deftu.coffeecord.commands.impl.slash.options;

import com.google.gson.JsonObject;
import xyz.deftu.coffeecord.Coffeecord;
import xyz.deftu.coffeecord.entities.JsonSerializable;
import xyz.deftu.coffeecord.utils.JsonSerializableHelper;

public class SlashCommandChoice<T> implements JsonSerializable<JsonObject> {

    private final String name;
    private final T value;

    public SlashCommandChoice(String name, T value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public T getValue() {
        return value;
    }

    public JsonObject asJson() {
        JsonObject value = new JsonObject();

        value.addProperty("name", JsonSerializableHelper.check("Slash command choice", "name", name));
        value.add("value", Coffeecord.GSON.toJsonTree(JsonSerializableHelper.check("Slash command choice", "value", this.value)));

        return value;
    }

}