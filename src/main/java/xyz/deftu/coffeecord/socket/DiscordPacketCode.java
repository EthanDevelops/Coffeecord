package xyz.deftu.coffeecord.socket;

public enum DiscordPacketCode {

    UNKNOWN(-1),

    DISPATCH(0),
    HEARTBEAT(1),
    IDENTIFY(2),
    PRESENCE_UPDATE(3),
    VOICE_STATE_UPDATE(4),
    RESUME(6),
    RECONNECT(7),
    REQUEST_GUILD_MEMBERS(8),
    INVALID_SESSION(9),
    HELLO(10),
    HEARTBEAT_ACKNOWLEDGE(11);

    private final int code;

    DiscordPacketCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static DiscordPacketCode from(int code) {
        for (DiscordPacketCode value : values()) {
            if (value.getCode() == code) {
                return value;
            }
        }

        return DiscordPacketCode.UNKNOWN;
    }

}