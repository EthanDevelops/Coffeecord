package xyz.deftu.coffeecord.events.messages;

import xyz.deftu.coffeecord.DiscordClient;
import xyz.deftu.coffeecord.entities.channel.BaseChannel;
import xyz.deftu.coffeecord.entities.channel.GuildChannel;
import xyz.deftu.coffeecord.entities.guild.Guild;
import xyz.deftu.coffeecord.entities.message.Message;
import xyz.deftu.coffeecord.events.GenericEvent;

public class MessageReceivedEvent extends GenericMessageEvent {

    public MessageReceivedEvent(DiscordClient client, Message message) {
        super(client, message);
    }

    public Message reply(Message message) {
        return this.message.reply(message);
    }

}