package xyz.deftu.coffeecord.socket;

import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;

public enum GatewayIntent {

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

    GatewayIntent(int offset) {
        this.offset = offset;
    }

    public static EnumSet<GatewayIntent> of(GatewayIntent... intents) {
        return EnumSet.copyOf(Arrays.asList(intents));
    }

    public static int from(Collection<GatewayIntent> collection) {
        int value = 0;
        for (GatewayIntent intent : collection)
            value |= 1 << intent.offset;
        return value;
    }

}