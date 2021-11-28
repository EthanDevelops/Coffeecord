package xyz.deftu.blurp4j.events;

import xyz.deftu.blurp4j.DiscordClient;
import xyz.deftu.blurp4j.entities.channels.TextChannel;
import xyz.deftu.blurp4j.entities.messages.Message;
import xyz.deftu.blurp4j.entities.users.User;

public class MessageCreateEvent extends MessageEvent {
    public final User author;
    public MessageCreateEvent(DiscordClient client, TextChannel channel, Message message, User author) {
        super(client, channel, message);
        this.author = author;
        allowResponses();
        allowReplies();
    }
}