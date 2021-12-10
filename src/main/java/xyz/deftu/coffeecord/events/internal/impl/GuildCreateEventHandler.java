package xyz.deftu.coffeecord.events.internal.impl;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import xyz.deftu.coffeecord.Coffeecord;
import xyz.deftu.coffeecord.DiscordClient;
import xyz.deftu.coffeecord.entities.channel.BaseChannel;
import xyz.deftu.coffeecord.events.internal.BaseEventHandler;
import xyz.deftu.coffeecord.utils.JsonHelper;

import java.util.ArrayList;
import java.util.List;

public class GuildCreateEventHandler extends BaseEventHandler {

    public GuildCreateEventHandler(DiscordClient client) {
        super(client);
    }

    public void handle(JsonObject data) {
        Number idRaw = JsonHelper.getNumber(data, "id");
        if (idRaw != null) { /* Should be impossible... */
            JsonArray channelsRaw = JsonHelper.getArray(data, "channels");
            if (channelsRaw != null) {
                List<BaseChannel> channels = new ArrayList<>();
                for (JsonElement channelRaw : channelsRaw) {
                    if (channelRaw.isJsonObject()) {
                        channels.add(client.getEntityCreator().createChannel(channelRaw.getAsJsonObject()));
                    }
                }

                for (BaseChannel channel : channels) {
                    if (channel != null) {
                        client.getEntityCache().channelCache.put(channel.getId(), channel);
                    }
                }
            }

            client.getEntityCache().guildCache.put(idRaw.longValue(), client.getEntityCreator().createGuild(data));
        }
    }

}