package xyz.deftu.coffeecord.events;

import xyz.deftu.coffeecord.DiscordClient;
import xyz.deftu.coffeecord.entities.guild.Guild;

public class GuildReadyEvent extends GenericEvent {

    public final Guild guild;

    public GuildReadyEvent(DiscordClient client, Guild guild) {
        super(client);
        this.guild = guild;
    }

}