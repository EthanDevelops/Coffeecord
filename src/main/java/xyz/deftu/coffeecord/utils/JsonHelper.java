package xyz.deftu.coffeecord.utils;

import com.google.gson.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class JsonHelper {

    private static final Map<String, JsonElement> jsonCache = new ConcurrentHashMap<>();

    public static boolean isValid(String input) {
        if (jsonCache.containsKey(input)) {
            return true;
        }

        try {
            jsonCache.put(input, JsonParser.parseString(input));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isObject(String input) {
        JsonElement cached = jsonCache.get(input);
        if (cached != null) {
            return cached.isJsonObject();
        } else {
            return isValid(input) && isObject(input);
        }
    }

    public static boolean isArray(String input) {
        JsonElement cached = jsonCache.get(input);
        if (cached != null) {
            return cached.isJsonArray();
        } else {
            return isValid(input) && isArray(input);
        }
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

    public static Number getNumber(JsonObject object, String key) {
        JsonPrimitive primitive = getPrimitive(object, key);
        if (primitive != null && (primitive.isNumber() || primitive.isString())) {
            return primitive.getAsNumber();
        } else {
            return null;
        }
    }

    public static int getInt(JsonObject object, String key) {
        Number number = getNumber(object, key);
        return number == null ? -1 : number.intValue();
    }

    public static long getLong(JsonObject object, String key) {
        Number number = getNumber(object, key);
        return number == null ? -1 : number.longValue();
    }

    public static float getFloat(JsonObject object, String key) {
        Number number = getNumber(object, key);
        return number == null ? -1 : number.floatValue();
    }

    public static double getDouble(JsonObject object, String key) {
        Number number = getNumber(object, key);
        return number == null ? -1 : number.doubleValue();
    }

    public static byte getByte(JsonObject object, String key) {
        Number number = getNumber(object, key);
        return number == null ? -1 : number.byteValue();
    }

    public static short getShort(JsonObject object, String key) {
        Number number = getNumber(object, key);
        return number == null ? -1 : number.shortValue();
    }

}