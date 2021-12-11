package xyz.deftu.coffeecord.events.internal.impl;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import xyz.deftu.coffeecord.DiscordClient;
import xyz.deftu.coffeecord.entities.channel.GuildChannel;
import xyz.deftu.coffeecord.entities.guild.Guild;
import xyz.deftu.coffeecord.events.initialization.GuildReadyEvent;
import xyz.deftu.coffeecord.events.internal.BaseEventHandler;
import xyz.deftu.coffeecord.utils.JsonHelper;
import xyz.deftu.deftils.Multithreading;

public class GuildCreateEventHandler extends BaseEventHandler {

    public GuildCreateEventHandler(DiscordClient client) {
        super(client);
    }

    public void handle(JsonObject data) {
        long id = JsonHelper.getLong(data, "id");
        if (id != -1) {
            Multithreading.runAsync(() -> {
                Guild guild = client.getObjectCreator().createGuild(data);
                client.getDiscordCache().addGuild(id, guild);

                JsonArray channelsRaw = JsonHelper.getArray(data, "channels");
                if (channelsRaw != null) {
                    for (JsonElement channelRawRaw : channelsRaw) {
                        JsonObject channelRaw = channelRawRaw.getAsJsonObject();
                        if (channelRaw.has("id")) {
                            if (!channelRaw.has("guild_id")) {
                                channelRaw.addProperty("guild_id", id);
                            }

                            GuildChannel channel = (GuildChannel) client.getObjectCreator().createChannel(channelRaw);

                            client.getDiscordCache().addGuildChannel(id, channel);
                            client.getDiscordCache().addChannel(JsonHelper.getLong(channelRaw, "id"), channel);
                        }
                    }
                }

                client.getEventBus().post(new GuildReadyEvent(client, guild));
            });
        }
    }

}