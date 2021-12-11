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
                .addInterceptor(new CoffeecordRequestInterceptor())
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
            int code = response.code();
            ResponseBody body = response.body();
            if (code >= 200 && code <= 300) {
                value.complete(request.handleSuccess(response, response.message(), body, body == null ? null : body.string()));
            } else {
                try {
                    String bodyStr = body == null ? null : body.string();
                    logger.error("A request with type of \"{}\" failed with the code of {} and message of \"{}\".{}", request.getClass().getSimpleName(), code, response.message(), bodyStr == null ? "" : "\n" + bodyStr);
                    request.handleFailure(response, response.message(), body, bodyStr);
                } catch (Exception e) {
                    e.printStackTrace();
                }
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