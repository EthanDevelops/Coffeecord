package xyz.deftu.coffeecord.utils;

import xyz.deftu.deftils.Strings;

public class JsonSerializableHelper {

    public static String check(String type, String name, String value) {
        if (Strings.isNullOrEmpty(value))
            throw new IllegalStateException(type + " " + name + " cannot be null/empty.");
        return value;
    }

    public static <T> T check(String type, String name, T value) {
        if (value == null)
            throw new IllegalStateException(type + " " + name + " cannot be null.");
        return value;
    }

}