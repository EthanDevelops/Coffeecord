package xyz.deftu.coffeecord.entities.guild;

import xyz.deftu.deftils.exceptions.UnfinishedApiException;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;

import static xyz.deftu.coffeecord.entities.guild.GuildPermission.PermissionType.*;

public enum GuildPermission {

    CREATE_INSTANT_INVITE(0, BOTH, "Create Instant Invite"),
    KICK_MEMBERS(1, GUILD,"Kick Members"),
    BAN_MEMBERS(2, GUILD,"Ban Members"),
    ADMINISTRATOR(3, GUILD,"Administrator"),
    MANAGE_CHANNEL(4, BOTH,"Manage Channels"),
    MANAGE_SERVER(5, GUILD,"Manage Server"),
    MESSAGE_ADD_REACTION(6, BOTH, "Add Reactions"),
    VIEW_AUDIT_LOGS(7, GUILD, "View Audit Logs"),
    PRIORITY_SPEAKER(8, BOTH, "Priority Speaker"),
    VOICE_STREAM(9, BOTH, "Video"),
    VIEW_CHANNEL(10, BOTH, "View Channel(s)"),
    MESSAGE_SEND(11, BOTH, "Send Messages"),
    MESSAGE_TTS(12, BOTH, "Send TTS Messages"),
    MESSAGE_MANAGE(13, BOTH, "Manage Messages"),
    MESSAGE_EMBED_LINKS(14, BOTH, "Embed Links"),
    MESSAGE_ATTACH_FILES(15, BOTH, "Attach Files"),
    MESSAGE_HISTORY(16, BOTH, "Read History"),
    MESSAGE_MENTION_EVERYONE(17, BOTH, "Mention Everyone"),
    MESSAGE_EXT_EMOJI(18, BOTH, "Use External Emojis"),
    VIEW_GUILD_INSIGHTS(19, GUILD, "View Server Insights"),
    VOICE_CONNECT(20, BOTH, "Connect"),
    VOICE_SPEAK(21, BOTH, "Speak"),
    VOICE_MUTE_OTHERS(22, BOTH, "Mute Members"),
    VOICE_DEAF_OTHERS(23, BOTH, "Deafen Members"),
    VOICE_MOVE_OTHERS(24, BOTH, "Move Members"),
    VOICE_USE(25, BOTH, "Use Voice Activity"),
    NICKNAME_CHANGE(26, GUILD, "Change Nickname"),
    NICKNAME_MANAGE(27, GUILD, "Manage Nicknames"),
    MANAGE_PERMISSIONS(28, CHANNEL, "Manage Permissions"),
    MANAGE_WEBHOOKS(29, BOTH, "Manage Webhooks"),
    MANAGE_EMOJIS_AND_STICKERS(30, GUILD, "Manage Emojis and Stickers"),
    USE_APPLICATION_COMMANDS(31, BOTH, "Use Application Commands"),
    REQUEST_TO_SPEAK(32, BOTH, "Request to Speak"),
    MANAGE_THREADS(34, BOTH, "Manage Threads"),
    CREATE_PUBLIC_THREADS(35, BOTH, "Create Public Threads"),
    CREATE_PRIVATE_THREADS(36, BOTH, "Create Private Threads"),
    MESSAGE_EXT_STICKER(37, BOTH, "Use External Stickers"),
    MESSAGE_SEND_IN_THREADS(38, BOTH, "Send Messages in Threads"),
    VOICE_START_ACTIVITIES(39, BOTH, "Launch Activities in Voice Channels"),

    UNKNOWN(-1, BOTH, "Unknown");

    private final int offset;
    private final long raw;
    private final PermissionType type;
    private final String name;

    GuildPermission(int offset, PermissionType type, String name) {
        this.offset = offset;
        this.raw = 1L << offset;
        this.type = type;
        this.name = name;
    }

    public int getOffset() {
        return offset;
    }

    public PermissionType getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public static EnumSet<GuildPermission> from(long raw) {
        EnumSet<GuildPermission> value = EnumSet.noneOf(GuildPermission.class);
        if (raw == 0)
            return value;
        for (GuildPermission permission : values()) {
            if (permission != UNKNOWN && (raw & permission.raw) == permission.raw) {
                value.add(permission);
            }
        }

        return value;
    }

    public static long raw(List<GuildPermission> permissions) {
        throw new UnfinishedApiException();
    }

    public static long raw(GuildPermission... permissions) {
        return raw(Arrays.asList(permissions));
    }

    public enum PermissionType {
        GUILD,
        CHANNEL,
        BOTH
    }

}