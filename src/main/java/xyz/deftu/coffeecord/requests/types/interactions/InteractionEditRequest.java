package xyz.deftu.coffeecord.requests.types.interactions;

import okhttp3.Headers;
import okhttp3.Response;
import okhttp3.ResponseBody;
import xyz.deftu.coffeecord.DiscordClient;
import xyz.deftu.coffeecord.entities.message.Message;
import xyz.deftu.coffeecord.interactions.Interaction;
import xyz.deftu.coffeecord.requests.RequestRoute;
import xyz.deftu.coffeecord.requests.RestBody;
import xyz.deftu.coffeecord.requests.RestRequest;

public class InteractionEditRequest extends RestRequest<Void> {

    public InteractionEditRequest(DiscordClient client, Interaction interaction, Message message) {
        super(
                client,
                new RestBody(client, message.asJson()),
                RequestRoute.Webhook.EDIT_ORIGINAL,
                RequestRoute.Webhook.EDIT_ORIGINAL.compile(String.valueOf(client.getApplication().getId()), interaction.getToken()),
                Headers.of()
        );
    }

    public Void handleSuccess(Response response, String message, ResponseBody body, String bodyStr) {
        System.out.println("Edit: " + bodyStr);
        return null;
    }

}