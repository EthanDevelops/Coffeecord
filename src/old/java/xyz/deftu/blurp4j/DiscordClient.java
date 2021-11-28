package xyz.deftu.blurp4j;

import okhttp3.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import xyz.deftu.blurp4j.commands.SlashCommands;
import xyz.deftu.blurp4j.entities.EntityCreator;
import xyz.deftu.blurp4j.status.UserActivity;
import xyz.deftu.blurp4j.status.UserStatus;
import xyz.deftu.blurp4j.entities.messages.Message;
import xyz.deftu.blurp4j.internal.networking.DiscordSocket;
import xyz.deftu.blurp4j.internal.requests.SimpleCallback;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class DiscordClient extends Thread {

    public static final String NAME = "Blurp4J";
    private String token;

    private final Logger logger;
    private final DiscordSocket clientSocket;
    private final SimpleEventBus eventBus;
    private final SlashCommands slashCommands;

    private List<UserActivity> activities = new ArrayList<>();
    private UserStatus status = UserStatus.ONLINE;

    private final EntityCreator entityCreator;

    public DiscordClient() {
        this.logger = LogManager.getLogger(NAME);
        this.clientSocket = new DiscordSocket(URI.create("wss://gateway.discord.gg/?v=9&encoding=json"), this);
        this.eventBus = new SimpleEventBus();
        this.slashCommands = new SlashCommands(this);

        this.entityCreator = new EntityCreator(this);
    }

    public synchronized void login(String token) {
        this.token = token;

        clientSocket.addOpenListener((socket, handshake) -> {
            clientSocket.send(new JsonObject()
                    .add("op", 2)
                    .add("d", new JsonObject()
                            .add("token", token)
                            .add("properties", new JsonObject()
                                    .add("$os", System.getProperty("os.name"))
                                    .add("$browser", NAME)
                                    .add("$device", NAME))
                            .add("compress", true)
                            .add("intents", 513)));
        });
        clientSocket.connect();
        clientSocket.initialize();
    }

    public synchronized void send(long channelId, Message message, boolean debug) {
        String url = "https://discord.com/api/v9/channels/" + channelId + "/messages";

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", "Bot " + token)
                .addHeader("Content-Type", "application/json")
                .post(RequestBody.create(message.jsonify().getAsString().getBytes(StandardCharsets.UTF_8)))
                .build();
        client.newCall(request).enqueue(new SimpleCallback((call, exception) -> {
            if (debug) {
                logger.error("Unable to send message.", exception);
            }
        }, (call, response) -> {
            if (debug) {
                try {
                    logger.info("Message sent successfully!\n{}{}", message.jsonify(), response.body() == null ? "" : "\n" + response.body().string());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }));
    }

    public synchronized void send(long channelId, Message message) {
        send(channelId, message, false);
    }

    public DiscordClient setStatus(UserStatus status) {
        this.status = status;
        return this;
    }

    public DiscordClient setActivities(List<UserActivity> activities) {
        this.activities = activities;
        return this;
    }

    public DiscordClient addActivity(UserActivity activity) {
        activities.add(activity);
        return this;
    }

    public DiscordClient removeActivity(UserActivity activity) {
        activities.remove(activity);
        return this;
    }

    public DiscordClient removeActivity(int index) {
        activities.remove(index);
        return this;
    }

    public DiscordClient clearActivities() {
        activities.clear();
        return this;
    }

    public DiscordClient removeStatus() {
        this.status = UserStatus.ONLINE;
        return this;
    }

    public synchronized DiscordClient updateStatus() {
        JsonObject packet = new JsonObject();

        JsonObject data = new JsonObject();

        if (activities != null) {
            JsonArray activities = new JsonArray();
            for (UserActivity activity : this.activities) {
                activities.add(activity.jsonify());
            }
            data.add("activities", activities);
        }
        if (status != null)
            data.add("status", status.getKey());
        data.add("afk", false);
        data.add("since", System.currentTimeMillis());

        packet.add("op", DiscordOpcode.STATUS_UPDATE);
        packet.add("d", data);

        clientSocket.send(packet);
        return this;
    }

    public String getToken() {
        return token;
    }

    public Logger getLogger() {
        return logger;
    }

    public DiscordSocket getClientSocket() {
        return clientSocket;
    }

    public SimpleEventBus getEventBus() {
        return eventBus;
    }

    public SlashCommands getSlashCommands() {
        return slashCommands;
    }

    public List<UserActivity> getActivities() {
        return activities;
    }

    public UserStatus getStatus() {
        return status;
    }

    public EntityCreator getEntityCreator() {
        return entityCreator;
    }

}