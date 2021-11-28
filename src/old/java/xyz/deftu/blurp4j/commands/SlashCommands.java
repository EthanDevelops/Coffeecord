package xyz.deftu.blurp4j.commands;

import okhttp3.*;
import xyz.deftu.blurp4j.DiscordClient;
import xyz.deftu.blurp4j.internal.requests.SimpleCallback;
import xyz.qalcyo.json.entities.JsonArray;
import xyz.qalcyo.json.entities.JsonElement;
import xyz.qalcyo.json.entities.JsonObject;
import xyz.qalcyo.json.parser.JsonParser;
import xyz.qalcyo.mango.Lists;
import xyz.qalcyo.mango.Strings;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class SlashCommands {

    private final DiscordClient client;
    private final List<SlashCommand> commands;

    private long applicationId;

    public SlashCommands(DiscordClient client) {
        this.client = client;
        this.commands = Lists.newArrayList();
    }

    public SlashCommands initialize(long applicationId) {
        this.applicationId = applicationId;
        return this;
    }

    public SlashCommands register(long guildId, SlashCommand slashCommand) {
        ensureInitialized();

        String url = "https://discord.com/api/v9/applications/" + applicationId + "/guilds/" + guildId + "/commands";
        boolean registered = registered(url, slashCommand);
        if (!registered) {
            OkHttpClient httpClient = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(url)
                    .addHeader("Authorization", "Bot " + client.getToken())
                    .addHeader("Content-Type", "application/json")
                    .post(RequestBody.create(slashCommand.jsonify().getAsString().getBytes(StandardCharsets.UTF_8)))
                    .build();
            httpClient.newCall(request).enqueue(new SimpleCallback((call, e) -> {
                e.printStackTrace();
            }, (call, response) -> {}));
            commands.add(slashCommand);
        }
        return this;
    }

    public SlashCommands register(SlashCommand slashCommand) {
        ensureInitialized();

        String url = "https://discord.com/api/v9/applications/" + applicationId + "/commands";
        boolean registered = registered(url, slashCommand);
        if (!registered) {
            OkHttpClient httpClient = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(url)
                    .addHeader("Authorization", "Bot " + client.getToken())
                    .addHeader("Content-Type", "application/json")
                    .post(RequestBody.create(slashCommand.jsonify().getAsString().getBytes(StandardCharsets.UTF_8)))
                    .build();
            httpClient.newCall(request).enqueue(new SimpleCallback((call, exception) -> {
                exception.printStackTrace();
            }, ((call, response) -> {})));
            commands.add(slashCommand);
        }
        return this;
    }

    public SlashCommands update(long commandId, SlashCommand slashCommand) {
        ensureInitialized();

        String url = "https://discord.com/api/v9/applications/" + applicationId + "/commands/" + commandId;
        OkHttpClient httpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", "Bot " + client.getToken())
                .addHeader("Content-Type", "application/json")
                .patch(RequestBody.create(slashCommand.jsonify().getAsString().getBytes(StandardCharsets.UTF_8)))
                .build();
        httpClient.newCall(request).enqueue(new SimpleCallback((call, exception) -> {
            exception.printStackTrace();
        }, ((call, response) -> {})));
        return this;
    }

    public SlashCommands update(long guildId, long commandId, SlashCommand slashCommand) {
        ensureInitialized();

        String url = "https://discord.com/api/v9/applications/" + applicationId + "/guilds/" + guildId + "/commands/" + commandId;
        OkHttpClient httpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", "Bot " + client.getToken())
                .addHeader("Content-Type", "application/json")
                .patch(RequestBody.create(slashCommand.jsonify().getAsString().getBytes(StandardCharsets.UTF_8)))
                .build();
        httpClient.newCall(request).enqueue(new SimpleCallback((call, e) -> {
            e.printStackTrace();
        }, (call, response) -> {}));
        return this;
    }

    public SlashCommands delete(long commandId, SlashCommand slashCommand) {
        ensureInitialized();

        String url = "https://discord.com/api/v9/applications/" + applicationId + "/commands/" + commandId;
        OkHttpClient httpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", "Bot " + client.getToken())
                .addHeader("Content-Type", "application/json")
                .delete()
                .build();
        httpClient.newCall(request).enqueue(new SimpleCallback((call, e) -> {
            e.printStackTrace();
        }, (call, response) -> {}));
        commands.remove(slashCommand);
        return this;
    }

    public SlashCommands delete(long guildId, long commandId, SlashCommand slashCommand) {
        ensureInitialized();

        String url = "https://discord.com/api/v9/applications/" + applicationId + "/guilds/" + guildId + "/commands/" + commandId;
        OkHttpClient httpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", "Bot " + client.getToken())
                .addHeader("Content-Type", "application/json")
                .delete()
                .build();
        httpClient.newCall(request).enqueue(new SimpleCallback((call, e) -> {
            e.printStackTrace();
        }, (call, response) -> {}));
        commands.remove(slashCommand);
        return this;
    }

    public SlashCommands deleteUnregistered() {
        ensureInitialized();

        String url = "https://discord.com/api/v9/applications/" + applicationId + "/commands";
        OkHttpClient httpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("Authorization", "Bot " + client.getToken())
                .addHeader("Content-Type", "application/json")
                .build();

        httpClient.newCall(request).enqueue(new SimpleCallback((call, e) -> {}, (call, response) -> {
            try {
                ResponseBody body = response.body();
                if (body != null) {
                    String responseStr = body.string();
                    if (!Strings.isNullOrEmpty(responseStr)) {
                        JsonArray responseArr = JsonParser.parse(responseStr).getAsJsonArray();

                        for (JsonElement element : responseArr) {
                            if (element.isJsonObject()) {
                                JsonObject commandObject = element.getAsJsonObject();

                                List<String> ignored = Lists.newArrayList();
                                for (SlashCommand command : commands) {
                                    if (commandObject.hasKey("name") && commandObject.getAsString("name").equalsIgnoreCase(command.getName())) {
                                        ignored.add(command.getName());
                                    }
                                }

                                if (!ignored.contains(commandObject.getAsString("name"))) {
                                    Request removeRequest = new Request.Builder()
                                            .url(url + "/" + commandObject.getAsLong("id"))
                                            .addHeader("Authorization", "Bot " + client.getToken())
                                            .addHeader("Content-Type", "application/json")
                                            .delete()
                                            .build();
                                    client.getLogger().warn("Removing " + commandObject.getAsString("name") + " as it isn't currently a registered command.");
                                    httpClient.newCall(removeRequest).enqueue(new SimpleCallback((innerCall, e) -> {
                                        e.printStackTrace();
                                    }, (innerCall, innerResponse) -> {}));
                                }
                            }
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }));
        return this;
    }

    public JsonArray retrieveCommands() {
        String url = "https://discord.com/api/v9/applications/" + applicationId + "/commands";
        OkHttpClient httpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("Authorization", "Bot " + client.getToken())
                .addHeader("Content-Type", "application/json")
                .build();

        AtomicReference<JsonArray> value = new AtomicReference<>(new JsonArray());
        httpClient.newCall(request).enqueue(new SimpleCallback((call, e) -> e.printStackTrace(), (call, response) -> {
            try {
                ResponseBody body = response.body();
                if (body != null) {
                    String content = body.string();
                    if (!content.isEmpty()) {
                        value.set(JsonParser.parse(content).getAsJsonArray());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }));

        return value.get();
    }

    private boolean registered(String url, SlashCommand slashCommand) {
        ensureInitialized();

        OkHttpClient httpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("Authorization", "Bot " + client.getToken())
                .addHeader("Content-Type", "application/json")
                .build();

        AtomicBoolean value = new AtomicBoolean(false);
        httpClient.newCall(request).enqueue(new SimpleCallback((call, e) -> {}, (call, response) -> {
            try {
                ResponseBody body = response.body();
                if (body != null) {
                    String responseStr = body.string();
                    if (!Strings.isNullOrEmpty(responseStr)) {
                        JsonArray responseArr = JsonParser.parse(responseStr).getAsJsonArray();

                        for (JsonElement element : responseArr) {
                            if (element.isJsonObject()) {
                                JsonObject commandObject = element.getAsJsonObject();
                                if (commandObject.hasKey("name") && commandObject.getAsString("name").equalsIgnoreCase(slashCommand.getName())) {
                                    value.set(true);
                                    return;
                                }
                            }
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }));

        return value.get();
    }

    private void ensureInitialized() {
        if (applicationId == -1) {
            throw new IllegalStateException("Please initialize this client's slash commands before using them.");
        }
    }

}