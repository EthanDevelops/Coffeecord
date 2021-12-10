package xyz.deftu.coffeecord.requests.types;

import com.google.gson.JsonParser;
import okhttp3.Headers;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import xyz.deftu.coffeecord.DiscordClient;
import xyz.deftu.coffeecord.entities.guild.Guild;
import xyz.deftu.coffeecord.requests.RequestRoute;
import xyz.deftu.coffeecord.requests.RestBody;
import xyz.deftu.coffeecord.requests.RestRequest;
import xyz.deftu.coffeecord.utils.JsonHelper;

public class GuildRequest extends RestRequest<Guild> {

    public GuildRequest(DiscordClient client, long id) {
        super(
                client,
                new RestBody(),
                RequestRoute.Guild.GET_GUILD,
                RequestRoute.Guild.GET_GUILD.compile(String.valueOf(id)),
                Headers.of()
        );
    }

    public Guild handleSuccess(Response response, String message, ResponseBody body, String bodyStr) {
        if (bodyStr != null && JsonHelper.isObject(bodyStr)) {
            return client.getEntityCreator().createGuild(JsonParser.parseString(bodyStr).getAsJsonObject());
        } else {
            return null;
        }
    }

}