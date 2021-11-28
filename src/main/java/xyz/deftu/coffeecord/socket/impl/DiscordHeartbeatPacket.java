package xyz.deftu.coffeecord.socket.impl;

import xyz.deftu.coffeecord.DiscordClient;
import xyz.deftu.coffeecord.socket.DiscordPacket;
import xyz.deftu.coffeecord.socket.DiscordPacketCode;

public class DiscordHeartbeatPacket extends DiscordPacket {

    private final int sequenceNumber;

    public DiscordHeartbeatPacket(DiscordClient client, int sequenceNumber) {
        super(client, DiscordPacketCode.HEARTBEAT);
        this.sequenceNumber = sequenceNumber;
    }

    public DiscordHeartbeatPacket(DiscordClient client) {
        this(client, -1);
    }

    public void onSend() {
        addDataOverride(sequenceNumber == -1 ? null : sequenceNumber);
    }

}