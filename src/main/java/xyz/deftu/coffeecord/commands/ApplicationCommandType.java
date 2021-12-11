package xyz.deftu.coffeecord.commands;

public enum ApplicationCommandType {
    CHAT(1),
    USER(2),
    MESSAGE(3),

    UNKNOWN(-1);

    private final int value;
    ApplicationCommandType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static ApplicationCommandType from(int value) {
        for (ApplicationCommandType type : values()) {
            if (type.value == value) {
                return type;
            }
        }

        return UNKNOWN;
    }
}