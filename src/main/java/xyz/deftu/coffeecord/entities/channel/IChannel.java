package xyz.deftu.coffeecord.entities.channel;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import xyz.deftu.coffeecord.Coffeecord;
import xyz.deftu.coffeecord.DiscordClient;
import xyz.deftu.coffeecord.entities.IMentionable;
import xyz.deftu.coffeecord.entities.ISnowflake;
import xyz.deftu.coffeecord.entities.message.Message;

public interface IChannel extends ISnowflake, IMentionable {

    DiscordClient getClient();

    default void send(Message message) {
        DiscordClient client = getClient();
        Request request = new Request.Builder()
                .url(Coffeecord.API_URL + "/channels/" + getId() + "/messages")
                .post(RequestBody.create(client.getGson().toJson(message.asJson()), MediaType.get("application/json")))
                .build();
        client.getRequestManager().request(request, true);
    }

}