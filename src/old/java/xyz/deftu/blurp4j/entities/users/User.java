package xyz.deftu.blurp4j.entities.users;

import xyz.deftu.blurp4j.entities.messages.Message;

public class User {

    private final String username, avatarId;
    private final int discriminator, publicFlags;
    private final boolean bot, system;
    private final long id;

    public User(String username, String avatarId, int discriminator, int publicFlags, boolean bot, boolean system, long id) {
        this.username = username;
        this.avatarId = avatarId;
        this.discriminator = discriminator;
        this.publicFlags = publicFlags;
        this.bot = bot;
        this.system = system;
        this.id = id;
    }

    public void message(Message message, boolean debug) {

    }

    public void message(Message message) {
        message(message, false);
    }

    public String getUsername() {
        return username;
    }

    public String getAvatarId() {
        return avatarId;
    }

    public int getDiscriminator() {
        return discriminator;
    }

    public String getDiscriminatorAsString() {
        return String.valueOf(discriminator);
    }

    public int getFlags() {
        return publicFlags;
    }

    public boolean hasFlag(int flag) {
        UserFlag userFlag = UserFlag.fromRaw(flag);
        return userFlag != null && userFlag != UserFlag.NONE;
    }

    public boolean hasFlag(UserFlag flag) {
        return hasFlag(flag.getValue());
    }

    public boolean isBot() {
        return bot;
    }

}