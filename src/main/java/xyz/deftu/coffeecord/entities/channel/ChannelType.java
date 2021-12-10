package xyz.deftu.coffeecord.entities.channel;

public enum ChannelType {

    GUILD_TEXT(0),
    DIRECT(1),
    GUILD_VOICE(2),
    GROUP_DIRECT(3),
    GUILD_CATEGORY(4),
    GUILD_NEWS(5),
    GUILD_STORE(6),
    GUILD_NEWS_THREAD(10),
    GUILD_PUBLIC_THREAD(11),
    GUILD_PRIVATE_THREAD(12),
    GUILD_STAGE_VOICE(13),

    UNKNOWN(-1);

    private final int value;
    ChannelType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public boolean isGuild() {
        return this == GUILD_TEXT ||
                this == GUILD_VOICE ||
                this == GUILD_CATEGORY ||
                this == GUILD_NEWS ||
                this == GUILD_STORE ||
                this == GUILD_NEWS_THREAD ||
                this == GUILD_PUBLIC_THREAD ||
                this == GUILD_PRIVATE_THREAD ||
                this == GUILD_STAGE_VOICE;
    }

    public boolean isDirect() {
        return this == DIRECT ||
                this == GROUP_DIRECT;
    }

    public static ChannelType from(int value) {
        for (ChannelType type : values()) {
            if (type.value == value) {
                return type;
            }
        }

        return UNKNOWN;
    }

}