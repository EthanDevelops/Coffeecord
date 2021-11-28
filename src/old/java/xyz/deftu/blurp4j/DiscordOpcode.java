package xyz.deftu.blurp4j;

public enum DiscordOpcode {

    STATUS_UPDATE(3);

    private final int value;
    DiscordOpcode(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public String toString() {
        return String.valueOf(value);
    }

}