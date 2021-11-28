package xyz.deftu.blurp4j.entities.users;

public enum UserFlag {

    NONE(0),
    EMPLOYEE(1),
    PARTNER(1 << 1),
    HYPESQUAD_EVENTS(1 << 2),
    BUG_HUNTER_1(1 << 3),
    BRAVERY_HOUSE(1 << 6),
    BRILLIANCE_HOUSE(1 << 7),
    BALANCE_HOUSE(1 << 8),
    EARLY_SUPPORTER(1 << 9),
    TEAM_USER(1 << 10),
    BUG_HUNTER_2(1 << 14),
    VERIFIED_BOT(1 << 16),
    VERIFIED_BOT_DEVELOPER(1 << 17),
    CERTIFIED_MODERATOR(1 << 18);

    private final int value;
    UserFlag(int value) {
        this.value = value;
    }
    public int getValue() {
        return value;
    }

    public static UserFlag fromRaw(int flag) {
        for (UserFlag value : values()) {
            if (flag == value.value) {
                return value;
            }
        }

        return NONE;
    }

}