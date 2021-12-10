package xyz.deftu.coffeecord.requests;

import xyz.deftu.coffeecord.Coffeecord;

import static xyz.deftu.coffeecord.requests.HttpMethod.*;

public class RequestRoute {

    private final HttpMethod method;
    private final String route;

    public RequestRoute(HttpMethod method, String route) {
        this.method = method;
        this.route = route;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getRoute() {
        return route;
    }

    public String compile(String... replacements) {
        String route = this.route;
        for (String replacement : replacements)
            route = route.replaceFirst("\\{}", replacement);
        return Coffeecord.API_URL + "/" + route;
    }

    public static class Guild {
        public static final RequestRoute GET_VOICE_REGIONS = new RequestRoute(GET, "guilds/{}/regions");
    }

    public static class Channel {
        public static final RequestRoute SEND_MESSAGE = new RequestRoute(POST, "channels/{}/messages");
    }

    public static class Self {
        public static final RequestRoute GET_SELF_GUILDS = new RequestRoute(GET, "users/@me/guilds");
        public static final RequestRoute MODIFY_SELF = new RequestRoute(PATCH, "users/@me");
    }
}