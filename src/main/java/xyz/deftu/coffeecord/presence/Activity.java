package xyz.deftu.coffeecord.presence;

import com.google.gson.JsonObject;
import xyz.deftu.coffeecord.entities.JsonSerializable;
import xyz.deftu.deftils.Strings;

public class Activity implements JsonSerializable<JsonObject> {

    private final String name;
    private final ActivityType type;
    private final String url;

    public Activity(String name, ActivityType type, String url) {
        this.name = name;
        this.type = type;
        this.url = url;
    }

    public JsonObject asJson() {
        JsonObject value = new JsonObject();

        if (Strings.isNullOrEmpty(name))
            throw new IllegalStateException("Activity name cannot be null or empty.");
        if (type == null)
            throw new IllegalStateException("Activity type cannot be null or empty.");

        value.addProperty("name", name);
        value.addProperty("type", type.getId());
        if (url != null) {
            value.addProperty("url", url);
        }

        return value;
    }

    public static Activity playing(String name) {
        return new Activity(name, ActivityType.PLAYING, null);
    }

    public static Activity streaming(String name, String url) {
        return new Activity(name, ActivityType.STREAMING, url);
    }

    public static Activity listening(String name) {
        return new Activity(name, ActivityType.LISTENING, null);
    }

    public static Activity watching(String name) {
        return new Activity(name, ActivityType.WATCHING, null);
    }

    public static Activity competing(String name) {
        return new Activity(name, ActivityType.COMPETING, null);
    }

}