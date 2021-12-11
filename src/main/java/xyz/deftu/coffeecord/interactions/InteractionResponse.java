package xyz.deftu.coffeecord.interactions;

import com.google.gson.JsonObject;
import xyz.deftu.coffeecord.entities.JsonSerializable;
import xyz.deftu.coffeecord.utils.JsonSerializableHelper;

public class InteractionResponse implements JsonSerializable<JsonObject> {

    private final InteractionResponseType type;
    private final JsonObject data;

    public InteractionResponse(InteractionResponseType type, JsonObject data) {
        this.type = type;
        this.data = data;
    }

    public JsonObject asJson() {
        JsonObject value = new JsonObject();

        value.addProperty("type", JsonSerializableHelper.check("Interaction response", "type", type).getValue());
        value.add("data", data);

        return value;
    }

}