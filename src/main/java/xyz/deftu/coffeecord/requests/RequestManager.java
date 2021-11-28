package xyz.deftu.coffeecord.requests;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import xyz.deftu.coffeecord.DiscordClient;

public class RequestManager {

    private final DiscordClient client;
    private final OkHttpClient httpClient;

    public RequestManager(DiscordClient client) {
        this.client = client;
        this.httpClient = new OkHttpClient.Builder()
                .authenticator((route, response) -> response.request().newBuilder()
                        .header("Authorization", client.getToken())
                        .build())
                .build();
    }

    public void request(Request request) {

    }

    public DiscordClient getClient() {
        return client;
    }

}