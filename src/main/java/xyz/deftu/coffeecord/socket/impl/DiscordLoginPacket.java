package xyz.deftu.coffeecord.socket.impl;

import com.google.gson.JsonObject;
import org.apache.logging.log4j.LogManager;
import xyz.deftu.coffeecord.CoffeecordArguments;
import xyz.deftu.coffeecord.DiscordClient;
import xyz.deftu.coffeecord.socket.DiscordPacket;
import xyz.deftu.coffeecord.socket.DiscordPacketCode;

public class DiscordLoginPacket extends DiscordPacket {

    private final String token;

    public DiscordLoginPacket(DiscordClient client, String token) {
        super(client, DiscordPacketCode.IDENTIFY);
        this.token = token;
    }

    public void onSend() {
        addData("token", token);
        JsonObject properties = new JsonObject();
        properties.addProperty("$os", System.getProperty("os.name"));
        properties.addProperty("$browser", DiscordClient.NAME);
        properties.addProperty("$device", DiscordClient.NAME);
        addData("properties", properties);
        addData("compress", true);
        if (CoffeecordArguments.isSocketDebug()) {
            LogManager.getLogger("Coffeecord (DiscordLoginPacket)").info("Sent intents with login packet: {}", client.getIntents() | 513);
        }
        // TODO

        addData("intents", client.getIntents() | 513);
    }

}