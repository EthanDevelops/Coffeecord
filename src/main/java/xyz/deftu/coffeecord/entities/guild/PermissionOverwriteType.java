package xyz.deftu.coffeecord.entities.guild;

public enum PermissionOverwriteType {
    ROLE(0),
    MEMBER(1);

    private final int value;
    PermissionOverwriteType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static PermissionOverwriteType from(int value) {
        for (PermissionOverwriteType type : values()) {
            if (type.value == value) {
                return type;
            }
        }

        return null;
    }
}