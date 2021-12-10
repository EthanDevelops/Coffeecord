package xyz.deftu.coffeecord.socket;

import xyz.deftu.coffeecord.entities.user.UserFlag;

import java.util.*;

public enum GatewayIntent {

    GUILDS(0),
    GUILD_MEMBERS(1),
    GUILD_BANS(2),
    GUILD_EMOJIS(3),
    GUILD_WEBHOOKS(5),
    GUILD_INVITES(6),
    GUILD_VOICE_STATES(7),
    GUILD_PRESENCES(8),
    GUILD_MESSAGES(9),
    GUILD_MESSAGE_REACTIONS(10),
    GUILD_MESSAGE_TYPING(11),

    DIRECT_MESSAGES(12),
    DIRECT_MESSAGE_REACTIONS(13),
    DIRECT_MESSAGE_TYPING(14);

    private final int offset;
    private final int raw;

    GatewayIntent(int offset) {
        this.offset = offset;
        this.raw = 1 << offset;
    }

    public int getOffset() {
        return offset;
    }

    public int getRaw() {
        return raw;
    }

    public static List<GatewayIntent> of(int raw) {
        List<GatewayIntent> value = new ArrayList<>();
        if (raw == 0)
            return value;
        for (GatewayIntent intent : values()) {
            if ((raw & intent.raw) == intent.raw) {
                value.add(intent);
            }
        }

        return value;
    }

    public static int from(Collection<GatewayIntent> collection) {
        int value = 0;
        for (GatewayIntent intent : collection)
            value |= 1 << intent.offset;
        return value;
    }

}