package xyz.deftu.coffeecord.events.internal;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import xyz.deftu.coffeecord.DiscordClient;
import xyz.deftu.coffeecord.events.internal.impl.MessageCreateEventHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DiscordEventParser {

    private final DiscordClient client;
    private final Map<String, List<BaseEventHandler>> handlerRegistry = new HashMap<>();

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
                List<BaseEventHandler> handlers = handlerRegistry.get(name.getAsString());
                if (handlers != null) {
                    for (BaseEventHandler handler : handlers) {
                        if (handler != null) {
                            handler.handle(data);
                        }
                    }
                }
            }
        }
    }

    public DiscordClient getClient() {
        return client;
    }

    public Map<String, List<BaseEventHandler>> getHandlerRegistry() {
        return handlerRegistry;
    }

    public void addHandler(String name, BaseEventHandler handler) {
        handlerRegistry.putIfAbsent(name, new ArrayList<>());
        handlerRegistry.get(name).add(handler);
    }

}