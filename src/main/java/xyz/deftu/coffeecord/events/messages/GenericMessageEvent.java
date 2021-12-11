package xyz.deftu.coffeecord.events.messages;

import xyz.deftu.coffeecord.DiscordClient;
import xyz.deftu.coffeecord.entities.channel.BaseChannel;
import xyz.deftu.coffeecord.entities.channel.GuildChannel;
import xyz.deftu.coffeecord.entities.guild.Guild;
import xyz.deftu.coffeecord.entities.message.Message;
import xyz.deftu.coffeecord.events.GenericEvent;

public class GenericMessageEvent extends GenericEvent {

    public final Message message;
    public final BaseChannel channel;
    public final Guild guild;

    public GenericMessageEvent(DiscordClient client, Message message) {
        super(client);
        this.message = message;
        this.channel = message.getChannel();
        if (channel instanceof GuildChannel) {
            this.guild = ((GuildChannel) channel).getGuild();
        } else {
            this.guild = null;
        }
    }

    public boolean isFromGuild() {
        return guild != null;
    }

}