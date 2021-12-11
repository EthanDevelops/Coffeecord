package xyz.deftu.coffeecord.presence;

public enum ActivityType {
    PLAYING(0),
    STREAMING(1),
    LISTENING(2),
    WATCHING(3),
    COMPETING(5);

    private final int id;
    ActivityType(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}