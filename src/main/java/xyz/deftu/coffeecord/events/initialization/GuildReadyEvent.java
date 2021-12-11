package xyz.deftu.coffeecord.events.initialization;

import xyz.deftu.coffeecord.DiscordClient;
import xyz.deftu.coffeecord.entities.guild.Guild;
import xyz.deftu.coffeecord.events.GenericEvent;

public class GuildReadyEvent extends GenericEvent {

    public final Guild guild;

    public GuildReadyEvent(DiscordClient client, Guild guild) {
        super(client);
        this.guild = guild;
    }

}