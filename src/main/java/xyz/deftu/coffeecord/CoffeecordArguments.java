package xyz.deftu.coffeecord;

import java.util.regex.Pattern;

public final class CoffeecordArguments {

    private static final Pattern BOOLEAN_PATTERN = Pattern.compile("true|false", Pattern.CASE_INSENSITIVE);

    private CoffeecordArguments() {
    }

    public static boolean isSocketDebug() {
        String property = System.getProperty("coffeecord.socket.debug", "false");
        if (!BOOLEAN_PATTERN.matcher(property).matches()) {
            throw new IllegalArgumentException("JVM property 'coffeecord.socket.debug' must be of type boolean!");
        }

        return Boolean.parseBoolean(property);
    }

}