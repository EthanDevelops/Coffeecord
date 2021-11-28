package xyz.deftu.coffeecord.socket.impl;

import com.google.gson.JsonObject;
import org.apache.logging.log4j.LogManager;
import xyz.deftu.coffeecord.DiscordClient;
import xyz.deftu.coffeecord.socket.DiscordPacket;
import xyz.deftu.coffeecord.socket.DiscordPacketCode;
import xyz.qalcyo.mango.Multithreading;

import java.util.concurrent.TimeUnit;

public class DiscordHelloPacket extends DiscordPacket {

    public DiscordHelloPacket(DiscordClient client) {
        super(client, DiscordPacketCode.HELLO);
    }

    public void onReceive(JsonObject content, JsonObject data) {
        long interval = data.get("heartbeat_interval").getAsLong();
        Multithreading.schedule(() -> {
            client.getSocket().send(new DiscordHeartbeatPacket(client, getSequenceNumber()));
        }, interval, interval, TimeUnit.MILLISECONDS);
    }

}