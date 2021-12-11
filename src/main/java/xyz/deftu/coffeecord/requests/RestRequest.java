package xyz.deftu.coffeecord.requests;

import okhttp3.Headers;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import xyz.deftu.coffeecord.DiscordClient;

import java.util.function.Consumer;

public abstract class RestRequest<T> {

    protected final DiscordClient client;
    private final RequestBody body;
    private final RequestRoute route;
    private final String url;
    private final Headers headers;

    public RestRequest(DiscordClient client, RequestBody body, RequestRoute route, String url, Headers headers) {
        this.client = client;
        this.body = body;
        this.route = route;
        this.url = url;
        this.headers = headers;
    }

    public abstract T handleSuccess(Response response, String message, ResponseBody body, String bodyStr);
    public void handleFailure(Response response, String message, ResponseBody body, String bodyStr) {
    }

    public DiscordClient getClient() {
        return client;
    }

    public RequestBody getBody() {
        return body;
    }

    public RequestRoute getRoute() {
        return route;
    }

    public String getUrl() {
        return url;
    }

    public Headers getHeaders() {
        return headers;
    }

}