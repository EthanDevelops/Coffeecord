package xyz.deftu.blurp4j.internal.networking;

import xyz.deftu.blurp4j.DiscordClient;
import xyz.qalcyo.json.entities.JsonObject;

public abstract class DiscordSocketHandler {

    protected final DiscordSocket socket;
    protected final DiscordClient client;
    protected JsonObject data;

    public DiscordSocketHandler(DiscordSocket socket, DiscordClient client) {
        this.socket = socket;
        this.client = client;
    }

    public final void handle(JsonObject data) {
        this.data = data;
        internal(data);
    }

    public abstract void internal(JsonObject data);

}