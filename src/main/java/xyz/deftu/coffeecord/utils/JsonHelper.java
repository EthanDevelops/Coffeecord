package xyz.deftu.coffeecord.utils;

import com.google.gson.JsonParser;

public class JsonHelper {

    public static boolean isValid(String input) {
        try {
            JsonParser.parseString(input);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}