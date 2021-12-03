package xyz.deftu.coffeecord.entities.user;

import xyz.deftu.coffeecord.socket.GatewayIntent;

import java.util.*;

public enum UserFlag {

    NONE(-1, "None"),
    STAFF(0, "Discord Employee"),
    PARTNER(1, "Partnered Server Onwer"),

    BUG_HUNTER_1(3, "Bug Hunter Level 1"),
    BUG_HUNTER_2(14, "Bug Hunter Level 2"),

    HYPESQUAD_EVENTS(2, "HypeSquad Events"),
    HYPESQUAD_BRAVERY(6, "HypeSquad Bravery"),
    HYPESQUAD_BRILLIANCE(7, "HypeSquad Brilliance"),
    HYPESQUAD_BALANCE(8, "HypeSquad Balance"),

    EARLY_SUPPORTER(9, "Early Supporter"),
    TEAM_USER(10, "Team User"),

    VERIFIED_BOT(16, "Verified Bot"),
    VERIFIED_DEVELOPER(17, "Early Verified Bot Developer"),

    CERTIFIED_MODERATOR(18, "Discord Certified Moderator"),
    BOT_HTTP_INTERACTIONS(19, "HTTP Interactions Bot");

    private final int offset;
    private final int raw;
    private final String name;
    UserFlag(int offset, String name) {
        this.offset = offset;
        this.raw = 1 << offset;
        this.name = name;
    }

    public int getOffset() {
        return offset;
    }

    public String getName() {
        return name;
    }

    public static EnumSet<UserFlag> of(UserFlag... fields) {
        return EnumSet.copyOf(Arrays.asList(fields));
    }

    public static List<UserFlag> from(int flags) {
        List<UserFlag> value = new ArrayList<>();
        if (flags == 0)
            return value;
        for (UserFlag flag : values()) {
            if (flag != NONE && (flags & flag.raw) == flag.raw) {
                value.add(flag);
            }
        }
        
        return value;
    }

    public static int from(Collection<UserFlag> collection) {
        int value = 0;
        for (UserFlag flag : collection)
            value |= 1 << flag.offset;
        return value;
    }

}