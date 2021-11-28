package xyz.deftu.blurp4j.events;

import xyz.deftu.blurp4j.DiscordClient;
import xyz.deftu.blurp4j.entities.channels.TextChannel;
import xyz.deftu.blurp4j.entities.messages.MessageReference;
import xyz.deftu.blurp4j.entities.messages.Message;

public class MessageEvent extends GenericEvent {

    public final TextChannel channel;
    public final Message message;

    private boolean allowResponse;
    private boolean allowReplies;

    public MessageEvent(DiscordClient client, TextChannel channel, Message message) {
        super(client);
        this.channel = channel;
        this.message = message;
    }

    public void respond(Message message, boolean debug) {
        if (!allowResponse)
            throw new UnsupportedOperationException("This event cannot be used for responses!");
        client.send(channel.channelId, message, debug);
    }

    public void respond(Message message) {
        respond(message, false);
    }

    public void reply(Message message, boolean debug) {
        if (!allowReplies)
            throw new UnsupportedOperationException("This event cannot be used for replies!");
        respond(message.setReferencedMessage(new MessageReference(this.message.getMessageId(), this.message.getGuildId(), true)), debug);
    }

    public void reply(Message message) {
        reply(message, false);
    }

    protected void allowResponses() {
        allowResponse = true;
    }

    protected void allowReplies() {
        allowReplies = true;
    }

}