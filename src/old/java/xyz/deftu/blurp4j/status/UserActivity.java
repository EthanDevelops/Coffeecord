package xyz.deftu.blurp4j.status;

import xyz.deftu.blurp4j.interfaces.IJsonifiable;
import xyz.qalcyo.json.entities.JsonObject;

public class UserActivity implements IJsonifiable<JsonObject> {

    private final String text;
    private final UserActivityState state;

    public UserActivity(String text, UserActivityState state) {
        this.text = text;
        this.state = state;
    }

    public UserActivity(UserActivityState state) {
        this("", state);
    }

    public String getText() {
        return text;
    }

    public UserActivityState getState() {
        return state;
    }

    public JsonObject jsonify() {
        JsonObject value = new JsonObject();

        value.add("name", text);
        value.add("type", state);

        return value;
    }

}