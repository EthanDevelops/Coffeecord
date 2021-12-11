package xyz.deftu.coffeecord.requests.types;

import com.google.gson.JsonParser;
import okhttp3.Headers;
import okhttp3.Response;
import okhttp3.ResponseBody;
import xyz.deftu.coffeecord.DiscordClient;
import xyz.deftu.coffeecord.entities.channel.BaseChannel;
import xyz.deftu.coffeecord.requests.RequestRoute;
import xyz.deftu.coffeecord.requests.RestBody;
import xyz.deftu.coffeecord.requests.RestRequest;
import xyz.deftu.coffeecord.utils.JsonHelper;

public class ChannelRequest extends RestRequest<BaseChannel> {

    public ChannelRequest(DiscordClient client, long id) {
        super(
                client,
                new RestBody(),
                RequestRoute.Channel.GET_CHANNEL,
                RequestRoute.Channel.GET_CHANNEL.compile(String.valueOf(id)),
                Headers.of()
        );
    }

    public BaseChannel handleSuccess(Response response, String message, ResponseBody body, String bodyStr) {
        if (bodyStr != null && JsonHelper.isObject(bodyStr)) {
            return client.getObjectCreator().createChannel(JsonParser.parseString(bodyStr).getAsJsonObject());
        } else {
            return null;
        }
    }

}