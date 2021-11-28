package xyz.deftu.blurp4j.events;

import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import xyz.deftu.blurp4j.DiscordClient;
import xyz.deftu.blurp4j.entities.messages.Message;
import xyz.qalcyo.json.entities.JsonObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class GenericInteractionEvent extends GenericEvent {

    public final JsonObject interactionData;

    public GenericInteractionEvent(DiscordClient client, JsonObject interactionData) {
        super(client);
        this.interactionData = interactionData;
    }

    public void respond(Message message, boolean ephemeral, boolean debug) {
        JsonObject object = message.jsonify();
        if (ephemeral)
            object.add("flags", 64);
        send(4, object, debug);
    }

    public void respond(Message message, boolean ephemeral) {
        respond(message, ephemeral, false);
    }

    public void respond(Message message) {
        respond(message, false);
    }

    public void defer(boolean debug) {
        send(6, new JsonObject(), debug);
    }

    public void defer() {
        defer(false);
    }

    public void edit(Message message, boolean debug) {
        send(7, message.jsonify(), debug);
    }

    public void edit(Message message) {
        edit(message, false);
    }

    private void send(int type, JsonObject data, boolean debug) {
        String url = "https://discord.com/api/v9/interactions/" + interactionData.getAsString("id") + "/" + interactionData.getAsString("token") + "/callback";

        OkHttpClient httpClient = new OkHttpClient();
        Request.Builder request = new Request.Builder()
                .url(url)
                .addHeader("Content-Type", "application/json");
        request.post(RequestBody.create(new JsonObject().add("type", type).add("data", data).getAsString().getBytes(StandardCharsets.UTF_8)));
        httpClient.newCall(request.build()).enqueue(new Callback() {
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                throw new RuntimeException(e);
            }
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (debug) {
                    System.out.println(response.body().string());
                }
            }
        });
    }

}