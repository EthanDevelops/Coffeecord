package xyz.deftu.coffeecord.interactions;

public enum InteractionResponseType {
    PONG(1),
    CHANNEL_MESSAGE_WITH_SOURCE(4),
    DEFERRED_CHANNEL_MESSAGE_WITH_SOURCE(5),
    DEFERRED_UPDATE_MESSAGE(6),
    UPDATE_MESSAGE(7),
    APPLICATION_COMMAND_AUTOCOMPLETE_RESULT(8);

    private final int value;
    InteractionResponseType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static InteractionResponseType from(int value) {
        for (InteractionResponseType type : values()) {
            if (type.value == value) {
                return type;
            }
        }

        return null;
    }
}