package xyz.deftu.coffeecord;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.regex.Pattern;

public class Coffeecord {

    public static final String NAME = "Coffeecord";

    public static final String API_URL = "https://discord.com/api/v9";
    public static final String CDN_URL = "https://cdn.discordapp.com/";

    private static final Pattern BOOLEAN_PATTERN = Pattern.compile("true|false", Pattern.CASE_INSENSITIVE);

    private Coffeecord() {
    }

    /* JVM properties. */

    public static boolean isSocketDebug() {
        String property = System.getProperty("coffeecord.socket.debug", "false");
        if (!BOOLEAN_PATTERN.matcher(property).matches()) {
            throw new IllegalArgumentException("JVM property 'coffeecord.socket.debug' must be of type boolean!");
        }

        return Boolean.parseBoolean(property);
    }

    public static boolean isRequestDebug() {
        String property = System.getProperty("coffeecord.request.debug", "false");
        if (!BOOLEAN_PATTERN.matcher(property).matches()) {
            throw new IllegalArgumentException("JVM property 'coffeecord.request.debug' must be of type boolean!");
        }

        return Boolean.parseBoolean(property);
    }

    /* Logging. */

    public static Logger createLogger(String input) {
        return LogManager.getLogger("Coffeecord (" + input + ")");
    }

    public static Logger createLogger(Class<?> clazz) {
        return createLogger(clazz.getSimpleName());
    }

}