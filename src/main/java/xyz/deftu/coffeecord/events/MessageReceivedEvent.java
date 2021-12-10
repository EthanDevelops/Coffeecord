package xyz.deftu.coffeecord.events;

import xyz.deftu.coffeecord.DiscordClient;
import xyz.deftu.coffeecord.entities.channel.BaseChannel;
import xyz.deftu.coffeecord.entities.message.Message;

public class MessageReceivedEvent extends GenericEvent {
    public final Message message;
    public final BaseChannel channel;

    public MessageReceivedEvent(DiscordClient client, Message message) {
        super(client);
        this.message = message;
        this.channel = message.getChannel();
    }

    public Message reply(Message message) {
        return this.message.reply(message);
    }
}