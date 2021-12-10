package xyz.deftu.coffeecord;

import xyz.deftu.coffeecord.requests.types.SelfUsernameModifyRequest;

public class SelfApplication {

    private final DiscordClient client;

    public SelfApplication(DiscordClient client) {
        this.client = client;
    }

    public void setUsername(String username) {
        client.getRestRequester().request(new SelfUsernameModifyRequest(client, username));
    }

}