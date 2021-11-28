package xyz.deftu.blurp4j.status;

public enum UserStatus {

    ONLINE("online"),
    IDLE("idle"),
    DND("dnd"),
    INVISIBLE("invisible"),
    OFFLINE("offline");

    private final String key;
    UserStatus(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public static UserStatus fromKey(String key) {
        for (UserStatus value : values()) {
            if (value.key.equalsIgnoreCase(key)) {
                return value;
            }
        }

        return null;
    }

}