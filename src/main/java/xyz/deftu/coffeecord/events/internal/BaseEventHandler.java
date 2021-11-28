package xyz.deftu.coffeecord.events.internal;

import com.google.gson.JsonObject;
import xyz.deftu.coffeecord.DiscordClient;

public abstract class BaseEventHandler {

    protected final DiscordClient client;

    public BaseEventHandler(DiscordClient client) {
        this.client = client;
    }

    public abstract void handle(JsonObject data);

}