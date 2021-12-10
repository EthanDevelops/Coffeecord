package xyz.deftu.coffeecord.requests.types;

import com.google.gson.JsonParser;
import okhttp3.*;
import xyz.deftu.coffeecord.DiscordClient;
import xyz.deftu.coffeecord.entities.message.Message;
import xyz.deftu.coffeecord.requests.RequestRoute;
import xyz.deftu.coffeecord.requests.RestBody;
import xyz.deftu.coffeecord.requests.RestRequest;
import xyz.deftu.coffeecord.utils.JsonHelper;

public class MessageSendRequest extends RestRequest<Message> {

    public MessageSendRequest(DiscordClient client, Message message, long channelId) {
        super(
                client,
                new RestBody(client, message.asJson()),
                RequestRoute.Channel.SEND_MESSAGE,
                RequestRoute.Channel.SEND_MESSAGE.compile(String.valueOf(channelId)),
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