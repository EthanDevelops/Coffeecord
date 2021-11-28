package xyz.deftu.blurp4j.status;

public enum UserActivityState {

    PLAYING(0),
    STREAMING(1),
    LISTENING(2),
    WATCHING(3),
    CUSTOM(4);

    private final int value;
    UserActivityState(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public String toString() {
        return String.valueOf(value);
    }

}