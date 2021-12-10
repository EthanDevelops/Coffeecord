package xyz.deftu.coffeecord.entities.user;

import xyz.deftu.coffeecord.DiscordClient;
import xyz.deftu.coffeecord.requests.types.SelfUsernameModifyRequest;

public class SelfUser extends User {

    private final DiscordClient client;

    public SelfUser(DiscordClient client, long id, String username, String discriminator, String avatar, String banner, int flags) {
        super(client, id, username, discriminator, avatar, true, false, banner, flags);
        this.client = client;
    }

    public void setUsername(String username) {
        client.getRestRequester().request(new SelfUsernameModifyRequest(client, username));
    }

}