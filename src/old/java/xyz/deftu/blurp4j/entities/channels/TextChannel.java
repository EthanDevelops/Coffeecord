package xyz.deftu.blurp4j.entities.channels;

public class TextChannel {

    public final long guildId;
    public final long channelId;

    public TextChannel(long guildId, long channelId) {
        this.guildId = guildId;
        this.channelId = channelId;
    }

}