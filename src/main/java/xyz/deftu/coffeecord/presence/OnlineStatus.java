package xyz.deftu.coffeecord.presence;

public enum OnlineStatus {
    ONLINE("online"),
    DO_NOT_DISTURB("dnd"),
    IDLE("idle"),
    INVISIBLE("invisible");

    private final String value;
    OnlineStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}