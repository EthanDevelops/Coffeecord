package xyz.deftu.coffeecord.socket.impl;

import com.google.gson.JsonObject;
import org.jetbrains.annotations.Nullable;
import xyz.deftu.coffeecord.DiscordClient;
import xyz.deftu.coffeecord.socket.DiscordPacket;
import xyz.deftu.coffeecord.socket.DiscordPacketCode;

public class DiscordDispatchPacket extends DiscordPacket {

    public DiscordDispatchPacket(DiscordClient client) {
        super(client, DiscordPacketCode.DISPATCH);
    }

    public void onReceive(JsonObject content, @Nullable JsonObject data) {
        client.getEventParser().parse(content, data);
    }

}