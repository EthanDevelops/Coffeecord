package xyz.deftu.coffeecord.requests.types;

import com.google.gson.JsonParser;
import okhttp3.Headers;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import xyz.deftu.coffeecord.DiscordClient;
import xyz.deftu.coffeecord.entities.message.Message;
import xyz.deftu.coffeecord.requests.RequestRoute;
import xyz.deftu.coffeecord.requests.RestBody;
import xyz.deftu.coffeecord.requests.RestRequest;
import xyz.deftu.coffeecord.utils.JsonHelper;

public class ChannelMessageRequest extends RestRequest<Message> {

    public ChannelMessageRequest(DiscordClient client, long id, long message) {
        super(
                client,
                new RestBody(),
                RequestRoute.Channel.GET_MESSAGE,
                RequestRoute.Channel.GET_MESSAGE.compile(String.valueOf(id), String.valueOf(message)),
                Headers.of()
        );
    }

    public Message handleSuccess(Response response, String message, ResponseBody body, String bodyStr) {
        if (bodyStr != null && JsonHelper.isObject(bodyStr)) {
            return client.getEntityCreator().createMessage(JsonParser.parseString(bodyStr).getAsJsonObject());
        } else {
            return null;
        }
    }

}