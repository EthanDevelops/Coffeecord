package xyz.deftu.coffeecord;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import xyz.deftu.coffeecord.entities.ISnowflake;
import xyz.deftu.coffeecord.events.internal.DiscordEventParser;
import xyz.deftu.coffeecord.requests.RequestManager;
import xyz.deftu.coffeecord.socket.DiscordSocket;
import xyz.deftu.coffeecord.socket.impl.DiscordLoginPacket;
import xyz.deftu.coffeecord.socket.GatewayIntent;
import xyz.deftu.eventbus.SimpleEventBus;

import java.net.URI;
import java.util.Collection;

public class DiscordClient implements ISnowflake {

    private final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .serializeNulls()
            .setLenient()
            .create();

    private String token;
    private boolean started;
    private long id = -1;

    private final DiscordSocket socket;
    private final SimpleEventBus eventBus = new SimpleEventBus();
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

    public DiscordClient login(String token) {
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

        return this;
    }

    public DiscordClient login() {
        return login(token);
    }

    public DiscordClient setToken(String token) {
        if (this.token != null)
            throw new IllegalStateException("Token has already been set, can't modify it now!");
        this.token = token;
        return this;
    }

    public int getIntents() {
        return intents;
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

    public long getId() {
        return id;
    }

    public DiscordSocket getSocket() {
        return socket;
    }

    public SimpleEventBus getEventBus() {
        return eventBus;
    }

    public DiscordEventParser getEventParser() {
        return eventParser;
    }

    public RequestManager getRequestManager() {
        return requestManager;
    }

    public void initializeId(long id) {
        if (this.id != -1)
            throw new IllegalStateException("ID has already be initialized.");
        this.id = id;
    }

}