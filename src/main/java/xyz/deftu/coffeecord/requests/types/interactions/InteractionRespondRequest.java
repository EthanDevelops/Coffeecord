package xyz.deftu.coffeecord.requests.types.interactions;

import okhttp3.Headers;
import okhttp3.Response;
import okhttp3.ResponseBody;
import xyz.deftu.coffeecord.DiscordClient;
import xyz.deftu.coffeecord.interactions.Interaction;
import xyz.deftu.coffeecord.interactions.InteractionResponse;
import xyz.deftu.coffeecord.requests.RequestRoute;
import xyz.deftu.coffeecord.requests.RestBody;
import xyz.deftu.coffeecord.requests.RestRequest;

public class InteractionRespondRequest extends RestRequest<Void> {

    public InteractionRespondRequest(DiscordClient client, Interaction interaction, InteractionResponse response) {
        super(
                client,
                new RestBody(client, response.asJson()),
                RequestRoute.Interaction.CREATE_RESPONSE,
                RequestRoute.Interaction.CREATE_RESPONSE.compile(String.valueOf(interaction.getId()), interaction.getToken()),
                Headers.of()
        );
    }

    public Void handleSuccess(Response response, String message, ResponseBody body, String bodyStr) {
        return null;
    }

}