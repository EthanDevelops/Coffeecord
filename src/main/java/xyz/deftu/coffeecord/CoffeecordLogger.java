package xyz.deftu.coffeecord;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CoffeecordLogger {

    public static Logger create(String input) {
        return LogManager.getLogger("Coffeecord (" + input + ")");
    }

    public static Logger create(Class<?> clazz) {
        return create(clazz.getSimpleName());
    }

}