package xyz.deftu.coffeecord.requests.types;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import okhttp3.Headers;
import okhttp3.Response;
import okhttp3.ResponseBody;
import xyz.deftu.coffeecord.DiscordClient;
import xyz.deftu.coffeecord.entities.guild.Guild;
import xyz.deftu.coffeecord.requests.RequestRoute;
import xyz.deftu.coffeecord.requests.RestBody;
import xyz.deftu.coffeecord.requests.RestRequest;
import xyz.deftu.coffeecord.utils.JsonHelper;

import java.util.ArrayList;
import java.util.List;

public class SelfGuildsRequest extends RestRequest<List<Guild>> {

    public SelfGuildsRequest(DiscordClient client) {
        super(
                client,
                new RestBody(),
                RequestRoute.Self.GET_SELF_GUILDS,
                RequestRoute.Self.GET_SELF_GUILDS.compile(),
                Headers.of()
        );
    }

    public List<Guild> handleSuccess(Response response, String message, ResponseBody body, String bodyStr) {
        if (bodyStr != null && JsonHelper.isArray(bodyStr)) {
            List<Guild> value = new ArrayList<>();
            JsonArray array = JsonParser.parseString(bodyStr).getAsJsonArray();
            System.out.println("Guild array: " + array);
            for (JsonElement guild : array) {
                if (guild.isJsonObject()) {
                    value.add(client.getObjectCreator().createGuild(guild.getAsJsonObject()));
                }
            }

            return value;
        } else {
            return null;
        }
    }

}