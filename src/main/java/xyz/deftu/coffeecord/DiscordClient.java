package xyz.deftu.coffeecord;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import xyz.deftu.coffeecord.entities.EntityCache;
import xyz.deftu.coffeecord.entities.EntityCreator;
import xyz.deftu.coffeecord.entities.ISnowflake;
import xyz.deftu.coffeecord.entities.user.SelfUser;
import xyz.deftu.coffeecord.events.internal.DiscordEventParser;
import xyz.deftu.coffeecord.requests.RestRequester;
import xyz.deftu.coffeecord.socket.DiscordSocket;
import xyz.deftu.coffeecord.socket.impl.DiscordIdentifyPacket;
import xyz.deftu.coffeecord.socket.GatewayIntent;
import xyz.deftu.eventbus.SimpleEventBus;

import java.net.URI;
import java.util.*;

public class DiscordClient implements ISnowflake {

    private final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .serializeNulls()
            .setLenient()
            .create();

    private String token;
    private boolean started;
    private long id = -1;

    private final EntityCache entityCache;
    private final DiscordSocket socket;
    private final SimpleEventBus eventBus = new SimpleEventBus();
    private final DiscordEventParser eventParser;
    private final EntityCreator entityCreator;
    private final RestRequester restRequester;
    private SelfUser selfUser;

    private UUID sessionId;

    private int intents = 0;

    public DiscordClient(String token) {
        this.token = token;
        this.intents |= GatewayIntent.from(Collections.singletonList(GatewayIntent.GUILDS));
        entityCache = new EntityCache();
        socket = new DiscordSocket(URI.create("wss://gateway.discord.gg/?v=9&encoding=json"), this);
        eventParser = new DiscordEventParser(this);
        entityCreator = new EntityCreator(this);
        restRequester = new RestRequester(this);
    }

    public DiscordClient() {
        this(null);
    }

    public DiscordClient login(String token) {
        if (!started) {
            this.token = token;
            boolean connected = socket.awaitConnect();
            if (connected) {
                socket.send(new DiscordIdentifyPacket(this, token));
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
        List<GatewayIntent> list = Arrays.asList(intents);
        if (list.contains(GatewayIntent.GUILDS))
            throw new IllegalArgumentException("GUILDS is already enabled by default, don't even try it!");
        int value = GatewayIntent.from(list);
        this.intents |= value;
        return this;
    }

    public DiscordClient addIntents(Collection<GatewayIntent> intents) {
        if (intents.contains(GatewayIntent.GUILDS))
            throw new IllegalArgumentException("GUILDS is already enabled by default, don't even try it!");
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

    public EntityCache getEntityCache() {
        return entityCache;
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

    public EntityCreator getEntityCreator() {
        return entityCreator;
    }

    public RestRequester getRestRequester() {
        return restRequester;
    }

    public SelfUser getSelfUser() {
        return selfUser;
    }

    public void setSelfUser(SelfUser selfUser) {
        this.selfUser = selfUser;
    }

    public UUID getSessionId() {
        return sessionId;
    }

    public void setSessionId(UUID sessionId) {
        this.sessionId = sessionId;
    }

}