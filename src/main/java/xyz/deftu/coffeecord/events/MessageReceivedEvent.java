package xyz.deftu.coffeecord.events;

import xyz.deftu.coffeecord.DiscordClient;
import xyz.deftu.coffeecord.entities.message.Message;

public class MessageReceivedEvent extends GenericEvent {
    public final Message message;
    public MessageReceivedEvent(DiscordClient client, Message message) {
        super(client);
        this.message = message;
    }
}