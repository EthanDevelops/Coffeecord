package xyz.deftu.coffeecord.events.internal;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import xyz.deftu.coffeecord.DiscordClient;
import xyz.deftu.coffeecord.events.internal.impl.MessageCreateEventHandler;

import java.util.HashMap;
import java.util.Map;

public class DiscordEventParser {

    private final DiscordClient client;
    private final Map<String, BaseEventHandler> handlerRegistry = new HashMap<>();

    public DiscordEventParser(DiscordClient client) {
        this.client = client;
    }

    public void initialize() {
        addHandler("MESSAGE_CREATE", new MessageCreateEventHandler(client));
    }

    public void parse(JsonObject content, JsonObject data) {
        if (content.has("t")) {
            JsonElement name = content.get("t");
            if (name.isJsonPrimitive()) {
                BaseEventHandler handler = handlerRegistry.get(name.getAsString());
                if (handler != null) {
                    handler.handle(data);
                }
            }
        }
    }

    public DiscordClient getClient() {
        return client;
    }

    public Map<String, BaseEventHandler> getHandlerRegistry() {
        return handlerRegistry;
    }

    public void addHandler(String name, BaseEventHandler handler) {
        handlerRegistry.putIfAbsent(name, handler);
    }

}