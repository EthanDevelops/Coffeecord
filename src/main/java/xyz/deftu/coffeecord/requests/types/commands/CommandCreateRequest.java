package xyz.deftu.coffeecord.requests.types.commands;

import com.google.gson.JsonParser;
import okhttp3.Headers;
import okhttp3.Response;
import okhttp3.ResponseBody;
import xyz.deftu.coffeecord.DiscordClient;
import xyz.deftu.coffeecord.commands.ApplicationCommand;
import xyz.deftu.coffeecord.requests.RequestRoute;
import xyz.deftu.coffeecord.requests.RestBody;
import xyz.deftu.coffeecord.requests.RestRequest;
import xyz.deftu.coffeecord.utils.JsonHelper;

public class CommandCreateRequest extends RestRequest<ApplicationCommand> {

    public CommandCreateRequest(DiscordClient client, ApplicationCommand command) {
        super(
                client,
                new RestBody(client, command.asJson()),
                RequestRoute.Command.CREATE_COMMAND,
                RequestRoute.Command.CREATE_COMMAND.compile(String.valueOf(client.getApplication().getId())),
                Headers.of()
        );
    }

    public ApplicationCommand handleSuccess(Response response, String message, ResponseBody body, String bodyStr) {
        if (JsonHelper.isValid(bodyStr) && JsonHelper.isObject(bodyStr)) {
            return client.getObjectCreator().createApplicationCommand(JsonParser.parseString(bodyStr).getAsJsonObject());
        } else {
            return null;
        }
    }

}