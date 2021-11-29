package xyz.deftu.coffeecord;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.OkHttpClient;
import xyz.deftu.coffeecord.events.internal.DiscordEventParser;
import xyz.deftu.coffeecord.requests.RequestManager;
import xyz.deftu.coffeecord.socket.DiscordSocket;
import xyz.deftu.coffeecord.socket.impl.DiscordLoginPacket;
import xyz.deftu.coffeecord.socket.GatewayIntent;
import xyz.qalcyo.eventbus.QalcyoEventBus;

import java.net.URI;
import java.util.Collection;

public class DiscordClient extends Thread {

    public static final String NAME = "Coffeecord";

    private final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .serializeNulls()
            .setLenient()
            .create();

    private String token;
    private boolean started;

    private final DiscordSocket socket;
    private final QalcyoEventBus eventBus = new QalcyoEventBus();
    private final DiscordEventParser eventParser;
    private final RequestManager requestManager;

    private int intents = 0;

    public DiscordClient(String token) {
        this.token = token;
        socket = new DiscordSocket(URI.create("wss://gateway.discord.gg/?v=9&encoding=json"), this);
        eventParser = new DiscordEventParser(this);
        requestManager = new RequestManager(this);
    }

    public DiscordClient() {
        this(null);
    }

    public void login(String token) {
        if (!started) {
            this.token = token;
            boolean connected = socket.awaitConnect();
            if (connected) {
                socket.send(new DiscordLoginPacket(this, token));
                eventParser.initialize();

                started = true;
            } else {
                throw new RuntimeException("Failed to connect to Discord gateway.");
            }
        } else {
            throw new IllegalStateException("This client was already started...");
        }
    }

    public void login() {
        login(token);
    }

    public void setToken(String token) {
        if (this.token != null)
            throw new IllegalStateException("Token has already been set, can't modify it now!");
        this.token = token;
    }

    public DiscordClient addIntents(GatewayIntent... intents) {
        int value = GatewayIntent.from(GatewayIntent.of(intents));
        this.intents |= value;
        return this;
    }

    public DiscordClient addIntents(Collection<GatewayIntent> intents) {
        int value = GatewayIntent.from(intents);
        this.intents |= value;
        return this;
    }

    public Gson getGson() {
        return gson;
    }

    public String getToken() {
        return token;
    }

    public DiscordSocket getSocket() {
        return socket;
    }

    public QalcyoEventBus getEventBus() {
        return eventBus;
    }

    public DiscordEventParser getEventParser() {
        return eventParser;
    }

    public RequestManager getRequestManager() {
        return requestManager;
    }

    public int getIntents() {
        return intents;
    }

}