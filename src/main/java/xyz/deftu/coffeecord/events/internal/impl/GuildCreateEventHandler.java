package xyz.deftu.coffeecord.events.internal.impl;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import xyz.deftu.coffeecord.DiscordClient;
import xyz.deftu.coffeecord.events.internal.BaseEventHandler;
import xyz.deftu.coffeecord.utils.JsonHelper;
import xyz.deftu.deftils.Multithreading;

public class GuildCreateEventHandler extends BaseEventHandler {

    public GuildCreateEventHandler(DiscordClient client) {
        super(client);
    }

    public void handle(JsonObject data) {
        Number idRaw = JsonHelper.getNumber(data, "id");
        if (idRaw != null) { /* Should be impossible... */
            Multithreading.runAsync(() -> {
                JsonArray channelsRaw = JsonHelper.getArray(data, "channels");
                if (channelsRaw != null) {
                    for (JsonElement channelRaw : channelsRaw) {
                        JsonObject channel = channelRaw.getAsJsonObject();
                        if (channel.has("id")) {
                            client.getDiscordCache().addChannel(JsonHelper.getNumber(channel, "id").longValue(), channel);
                        }
                    }
                }

                client.getDiscordCache().addGuild(idRaw.longValue(), data);
            });
        }
    }

}