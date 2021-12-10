package xyz.deftu.coffeecord.utils;

import com.google.gson.*;

public class JsonHelper {

    public static boolean isValid(String input) {
        try {
            JsonParser.parseString(input);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isObject(String input) {
        return isValid(input) && JsonParser.parseString(input).isJsonObject();
    }

    public static boolean isArray(String input) {
        return isValid(input) && JsonParser.parseString(input).isJsonArray();
    }

    public static JsonElement getElement(JsonObject object, String key) {
        if (object.has(key)) {
            return object.get(key);
        } else {
            return null;
        }
    }

    public static JsonObject getObject(JsonObject object, String key) {
        JsonElement element = getElement(object, key);
        if (element != null && element.isJsonObject()) {
            return element.getAsJsonObject();
        } else {
            return null;
        }
    }

    public static JsonArray getArray(JsonObject object, String key) {
        JsonElement element = getElement(object, key);
        if (element != null && element.isJsonArray()) {
            return element.getAsJsonArray();
        } else {
            return null;
        }
    }

    public static JsonPrimitive getPrimitive(JsonObject object, String key) {
        JsonElement element = getElement(object, key);
        if (element != null && element.isJsonPrimitive()) {
            return element.getAsJsonPrimitive();
        } else {
            return null;
        }
    }

    public static String getString(JsonObject object, String key) {
        JsonPrimitive primitive = getPrimitive(object, key);
        if (primitive != null && primitive.isString()) {
            return primitive.getAsString();
        } else {
            return null;
        }
    }

    public static Number getNumber(JsonObject object, String key) {
        JsonPrimitive primitive = getPrimitive(object, key);
        if (primitive != null && (primitive.isNumber() || primitive.isString())) {
            return primitive.getAsNumber();
        } else {
            return null;
        }
    }

    public static boolean getBoolean(JsonObject object, String key) {
        JsonPrimitive primitive = getPrimitive(object, key);
        if (primitive != null && primitive.isBoolean()) {
            return primitive.getAsBoolean();
        } else {
            return false;
        }
    }

    public static char getChar(JsonObject object, String key) {
        JsonPrimitive primitive = getPrimitive(object, key);
        if (primitive != null && primitive.isString()) {
            return primitive.getAsCharacter();
        } else {
            return '.';
        }
    }

}