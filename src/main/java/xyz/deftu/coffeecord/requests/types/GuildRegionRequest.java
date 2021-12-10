package xyz.deftu.coffeecord.requests.types;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import okhttp3.Headers;
import okhttp3.Response;
import okhttp3.ResponseBody;
import xyz.deftu.coffeecord.DiscordClient;
import xyz.deftu.coffeecord.entities.guild.Guild;
import xyz.deftu.coffeecord.entities.guild.Region;
import xyz.deftu.coffeecord.requests.RequestRoute;
import xyz.deftu.coffeecord.requests.RestBody;
import xyz.deftu.coffeecord.requests.RestRequest;
import xyz.deftu.coffeecord.utils.JsonHelper;

import java.util.ArrayList;
import java.util.List;

public class GuildRegionRequest extends RestRequest<List<Region>> {

    private final boolean deprecated;

    public GuildRegionRequest(DiscordClient client, Guild guild, boolean deprecated) {
        super(
                client,
                new RestBody(),
                RequestRoute.Guild.GET_VOICE_REGIONS,
                RequestRoute.Guild.GET_VOICE_REGIONS.compile(String.valueOf(guild.getId())),
                Headers.of()
        );
        this.deprecated = deprecated;
    }

    public List<Region> handleSuccess(Response response, String message, ResponseBody body, String bodyStr) {
        if (bodyStr != null && JsonHelper.isArray(bodyStr)) {
            List<Region> value = new ArrayList<>();
            JsonArray array = JsonParser.parseString(bodyStr).getAsJsonArray();
            for (JsonElement region : array) {
                if (region.isJsonObject()) {
                    JsonObject object = region.getAsJsonObject();
                    if (!deprecated && object.get("deprecated").getAsBoolean())
                        continue;
                    String id = object.get("id").getAsString();
                    Region instance = Region.from(id);
                    if (instance != Region.UNKNOWN) {
                        value.add(instance);
                    }
                }
            }

            return value;
        } else {
            return null;
        }
    }

}