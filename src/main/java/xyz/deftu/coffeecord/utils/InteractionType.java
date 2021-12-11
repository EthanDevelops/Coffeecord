package xyz.deftu.coffeecord.utils;

public enum InteractionType {
    PING(1),
    APPLICATION_COMMAND(2),
    COMPONENT(3),

    UNKNOWN(-1);

    private final int value;
    InteractionType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static InteractionType from(int value) {
        for (InteractionType type : values()) {
            if (type.value == value) {
                return type;
            }
        }

        return UNKNOWN;
    }
}