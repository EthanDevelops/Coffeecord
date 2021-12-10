package xyz.deftu.coffeecord.socket.impl;

import com.google.gson.JsonObject;
import org.apache.logging.log4j.LogManager;
import xyz.deftu.coffeecord.Coffeecord;
import xyz.deftu.coffeecord.DiscordClient;
import xyz.deftu.coffeecord.socket.DiscordPacket;
import xyz.deftu.coffeecord.socket.DiscordPacketCode;
import xyz.deftu.coffeecord.socket.GatewayIntent;

public class DiscordIdentifyPacket extends DiscordPacket {

    private final String token;

    public DiscordIdentifyPacket(DiscordClient client, String token) {
        super(client, DiscordPacketCode.IDENTIFY);
        this.token = token;
    }

    public void onSend() {
        addData("token", token);
        JsonObject properties = new JsonObject();
        properties.addProperty("$os", System.getProperty("os.name"));
        properties.addProperty("$browser", Coffeecord.NAME);
        properties.addProperty("$device", Coffeecord.NAME);
        addData("properties", properties);
        if (Coffeecord.isSocketDebug()) {
            LogManager.getLogger("Coffeecord (DiscordLoginPacket)").info("Sent intents with login packet: {}", GatewayIntent.of(client.getIntents()));
            LogManager.getLogger("Coffeecord (DiscordLoginPacket)").info("Sent intents with login packet: {}", client.getIntents());
        }

        addData("intents", client.getIntents());
    }

}