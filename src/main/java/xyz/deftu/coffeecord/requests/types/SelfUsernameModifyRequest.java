package xyz.deftu.coffeecord.requests.types;

import com.google.gson.JsonObject;
import okhttp3.Headers;
import okhttp3.Response;
import okhttp3.ResponseBody;
import xyz.deftu.coffeecord.DiscordClient;
import xyz.deftu.coffeecord.requests.RequestRoute;
import xyz.deftu.coffeecord.requests.RestBody;
import xyz.deftu.coffeecord.requests.RestRequest;

public class SelfUsernameModifyRequest extends RestRequest<Void> {

    public SelfUsernameModifyRequest(DiscordClient client, String username) {
        super(
                client,
                new RestBody(client, asJson(username)),
                RequestRoute.Self.MODIFY_SELF,
                RequestRoute.Self.MODIFY_SELF.compile(),
                Headers.of()
        );
    }

    public Void handleSuccess(Response response, String message, ResponseBody body, String bodyStr) {
        return null;
    }

    private static JsonObject asJson(String username) {
        JsonObject value = new JsonObject();
        value.addProperty("username", username);
        return value;
    }

}