package xyz.deftu.coffeecord.commands;

import xyz.deftu.coffeecord.DiscordClient;
import xyz.deftu.coffeecord.entities.guild.Guild;
import xyz.deftu.coffeecord.requests.types.commands.CommandCreateRequest;

public class DiscordCommandManager {

    private final DiscordClient client;
    private final Guild guild;

    public DiscordCommandManager(DiscordClient client, Guild guild) {
        this.client = client;
        this.guild = guild;
    }

    public DiscordCommandManager(DiscordClient client) {
        this(client, null);
    }

    public ApplicationCommand create(ApplicationCommand command) {
        return client.getRestRequester().request(new CommandCreateRequest(client, command));
    }

    public void update(ApplicationCommand command) {

    }

    public ApplicationCommand retrieve(long id) {
        return null;
    }

    public void remove(long id) {

    }

    public DiscordClient getClient() {
        return client;
    }

    public Guild getGuild() {
        return guild;
    }

    public boolean isGuildManager() {
        return guild != null;
    }

}