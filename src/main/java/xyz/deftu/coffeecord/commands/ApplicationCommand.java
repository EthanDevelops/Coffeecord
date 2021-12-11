package xyz.deftu.coffeecord.commands;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import xyz.deftu.coffeecord.entities.JsonSerializable;
import xyz.deftu.coffeecord.utils.JsonSerializableHelper;

import java.util.Map;

public abstract class ApplicationCommand implements JsonSerializable<JsonObject> {

    private final long id;
    private final ApplicationCommandType type;
    private final long guildId;
    private final String name;
    private final String description;
    private final boolean defaultPermission;

    public ApplicationCommand(long id, ApplicationCommandType type, long guildId, String name, String description, boolean defaultPermission) {
        this.id = id;
        this.type = type;
        this.guildId = guildId;
        this.name = name;
        this.description = description;
        this.defaultPermission = defaultPermission;
    }

    public long getId() {
        return id;
    }

    public ApplicationCommandType getType() {
        return type;
    }

    public long getGuildId() {
        return guildId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean isDefaultPermission() {
        return defaultPermission;
    }

    protected JsonObject asJsonExt() {
        return new JsonObject();
    }

    public final JsonObject asJson() {
        JsonObject value = new JsonObject();

        value.addProperty("name", JsonSerializableHelper.check("Application command", "name", name));
        value.addProperty("description", JsonSerializableHelper.check("Application command", "description", description));
        value.addProperty("type", JsonSerializableHelper.check("Application command", "type", type).getValue());
        value.addProperty("default_permission", defaultPermission);

        JsonObject ext = asJsonExt();
        if (ext != null) {
            for (Map.Entry<String, JsonElement> entry : ext.entrySet()) {
                value.add(entry.getKey(), entry.getValue());
            }
        }

        return value;
    }

}