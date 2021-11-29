package xyz.deftu.coffeecord.requests;

import okhttp3.*;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import xyz.deftu.coffeecord.Coffeecord;
import xyz.deftu.coffeecord.DiscordClient;

import java.io.IOException;

public class RequestManager {

    private final DiscordClient client;
    private final Logger logger = Coffeecord.createLogger(RequestManager.class);
    private final OkHttpClient httpClient;

    public RequestManager(DiscordClient client) {
        this.client = client;
        this.httpClient = new OkHttpClient.Builder()
                .build();
    }

    public void request(Request request, Callback callback, boolean authorized) {
        if (authorized)
            request = request.newBuilder().header("Authorization", "Bot " + client.getToken()).build();
        httpClient.newCall(request).enqueue(callback);
    }

    public void request(Request request, boolean authorized) {
        request(request, new Callback() {
            public void onResponse(@NotNull Call call, @NotNull Response response) {
                if (Coffeecord.isRequestDebug()) {
                    logger.info(response.message());
                }
            }
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                logger.error("An unexpected error occurred in an HTTP request.", e);
            }
        }, authorized);
    }

    public DiscordClient getClient() {
        return client;
    }

}