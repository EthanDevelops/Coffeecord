package xyz.deftu.coffeecord.commands.impl.slash;

public enum SlashCommandOptionType {
    SUBCOMMAND(1),
    SUBCOMMAND_GROUP(2),
    STRING(3, true),
    INTEGER(4, true),
    BOOLEAN(5),
    USER(6),
    CHANNEL(7),
    ROLE(8),
    MENTIONABLE(9),
    NUMBER(10, true);

    private final int value;
    private final boolean supportsChoices;
    SlashCommandOptionType(int value, boolean supportsChoices) {
        this.value = value;
        this.supportsChoices = supportsChoices;
    }

    SlashCommandOptionType(int value) {
        this(value, false);
    }

    public int getValue() {
        return value;
    }

    public boolean isSupportsChoices() {
        return supportsChoices;
    }

    public static SlashCommandOptionType from(int value) {
        for (SlashCommandOptionType type : values()) {
            if (type.value == value) {
                return type;
            }
        }

        return null;
    }
}