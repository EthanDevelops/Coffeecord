package xyz.deftu.blurp4j.entities.messages.components;

import xyz.deftu.blurp4j.interfaces.IJsonifiable;
import xyz.qalcyo.json.entities.JsonObject;

public abstract class MessageComponent implements IJsonifiable<JsonObject> {

    private final int type;

    public MessageComponent(int type) {
        this.type = type;
    }

    public abstract void json(JsonObject object);
    public final JsonObject jsonify() {
        JsonObject value = new JsonObject();
        value.add("type", type);
        json(value);
        return value;
    }

    public enum MessageComponentType {
        UNKNOWN(-1, 0),
        ACTION_ROW(1, 0),
        BUTTON(2, 5),
        SELECTION_MENU(3, 1);

        private final int key;
        private final int maxPerRow;
        MessageComponentType(int key, int maxPerRow) {
            this.key = key;
            this.maxPerRow = maxPerRow;
        }

        public int getMaxPerRow() {
            return maxPerRow;
        }

        public static MessageComponentType fromKey(int key) {
            for (MessageComponentType value : values()) {
                if (value.key == key) {
                    return value;
                }
            }

            return UNKNOWN;
        }
    }

}