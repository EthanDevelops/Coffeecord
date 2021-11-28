package xyz.deftu.blurp4j.internal.networking;

public enum DiscordSocketCode {

    DISPATCH(0),
    HEARTBEAT(1),
    IDENTIFY(2),
    PRESENCE(3),
    VOICE_STATE(4),
    RESUME(6),
    RECONNECT(7),
    MEMBER_CHUNK_REQUEST(8),
    INVALIDATE_SESSION(9),
    HELLO(10),
    HEARTBEAT_ACK(11),
    GUILD_SYNC(12);

    private final int dispatch;
    DiscordSocketCode(int dispatch) {
        this.dispatch = dispatch;
    }

    public int getDispatch() {
        return dispatch;
    }

    public static DiscordSocketCode fromOp(int op) {
        for (DiscordSocketCode value : values()) {
            if (value.dispatch == op) {
                return value;
            }
        }

        return null;
    }

}