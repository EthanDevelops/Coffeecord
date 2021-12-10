package xyz.deftu.coffeecord.requests.types;

import com.google.gson.JsonParser;
import okhttp3.Headers;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import xyz.deftu.coffeecord.DiscordClient;
import xyz.deftu.coffeecord.entities.user.User;
import xyz.deftu.coffeecord.requests.RequestRoute;
import xyz.deftu.coffeecord.requests.RestBody;
import xyz.deftu.coffeecord.requests.RestRequest;
import xyz.deftu.coffeecord.utils.JsonHelper;

public class UserRequest extends RestRequest<User> {

    public UserRequest(DiscordClient client, long id) {
        super(
                client,
                new RestBody(),
                RequestRoute.User.GET_USER,
                RequestRoute.User.GET_USER.compile(String.valueOf(id)),
                Headers.of()
        );
    }

    public User handleSuccess(Response response, String message, ResponseBody body, String bodyStr) {
        if (bodyStr != null && JsonHelper.isObject(bodyStr)) {
            return client.getEntityCreator().createUser(JsonParser.parseString(bodyStr).getAsJsonObject());
        } else {
            return null;
        }
    }

}