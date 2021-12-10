package xyz.deftu.coffeecord.requests;

import kotlin.Pair;
import okhttp3.*;
import org.apache.logging.log4j.Logger;
import xyz.deftu.coffeecord.Coffeecord;
import xyz.deftu.coffeecord.DiscordClient;

import java.util.concurrent.CompletableFuture;

public class RestRequester {

    private final DiscordClient client;
    private final Logger logger = Coffeecord.createLogger(RestRequester.class);
    private final OkHttpClient httpClient;

    public RestRequester(DiscordClient client) {
        this.client = client;
        this.httpClient = new OkHttpClient.Builder()
                .build();
    }

    public <T> T request(RestRequest<T> request) {
        try {
            CompletableFuture<T> value = new CompletableFuture<>();
            Request.Builder sent = new Request.Builder();

            request.getRoute().getMethod().apply(sent, request.getBody());
            String url = checkAuth(request.getUrl(), sent);
            sent.url(url);

            for (Pair<? extends String, ? extends String> header : request.getHeaders()) {
                if (header != null) {
                    sent.header(header.component1(), header.component2());
                }
            }

            Response response = httpClient.newCall(sent.build()).execute();
            if (response.code() == 200) {
                ResponseBody body = response.body();
                value.complete(request.handleSuccess(response, response.message(), body, body == null ? null : body.string()));
            }

            return value.getNow(null);
        } catch (Exception e) {
            logger.error("An unexpected error occurred during a request.", e);
            return null;
        }
    }

    private String checkAuth(String url, Request.Builder request) {
        if (url.startsWith(Coffeecord.API_URL))
            request.addHeader("Authorization", "Bot " + client.getToken());
        return url;
    }

    public DiscordClient getClient() {
        return client;
    }

}