package xyz.deftu.coffeecord.socket.impl;

import xyz.deftu.coffeecord.DiscordClient;
import xyz.deftu.coffeecord.socket.DiscordPacket;
import xyz.deftu.coffeecord.socket.DiscordPacketCode;

public class DiscordPresenceUpdatePacket extends DiscordPacket {

    public DiscordPresenceUpdatePacket(DiscordClient client) {
        super(client, DiscordPacketCode.PRESENCE_UPDATE);
    }

    public void onSend() {
        addDataOverride(client.getPresence().asJson());
    }

}