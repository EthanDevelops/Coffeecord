package xyz.deftu.coffeecord;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.logging.log4j.Logger;
import xyz.deftu.coffeecord.commands.DiscordCommandManager;
import xyz.deftu.coffeecord.entities.EntityCreator;
import xyz.deftu.coffeecord.entities.user.SelfUser;
import xyz.deftu.coffeecord.events.internal.DiscordEventParser;
import xyz.deftu.coffeecord.presence.Presence;
import xyz.deftu.coffeecord.requests.RestRequester;
import xyz.deftu.coffeecord.socket.DiscordSocket;
import xyz.deftu.coffeecord.socket.GatewayIntent;
import xyz.deftu.coffeecord.socket.impl.DiscordIdentifyPacket;
import xyz.deftu.eventbus.SimpleEventBus;

import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DiscordClient {

    private static long id = 0;

    private final Logger logger = Coffeecord.createLogger();
    private final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .serializeNulls()
            .setLenient()
            .create();

    private String token;

    private Object readyLock = new Object();

    private final DiscordCache discordCache;
    private final DiscordSocket socket;
    private final SimpleEventBus eventBus = new SimpleEventBus();
    private final DiscordEventParser eventParser;
    private final EntityCreator entityCreator;
    private final RestRequester restRequester;
    private final DiscordCommandManager globalCommandManager;
    private final Presence presence;
    private DiscordApplication application;
    private SelfUser selfUser;

    private UUID sessionId;

    private int intents = 0;

    public DiscordClient(String token) {
        this.token = token;
        this.intents |= GatewayIntent.from(Collections.singletonList(GatewayIntent.GUILDS));
        discordCache = new DiscordCache(this);
        socket = new DiscordSocket(Coffeecord.GATEWAY_URL, this);
        eventParser = new DiscordEventParser(this);
        entityCreator = new EntityCreator(this);
        restRequester = new RestRequester(this);
        globalCommandManager = new DiscordCommandManager(this);
        presence = new Presence(this);

        id++;
    }

    public DiscordClient() {
        this(null);
    }

    public DiscordClient login(String token) {
        this.token = token;
        boolean connected = socket.awaitConnect();
        if (connected) {
            socket.send(new DiscordIdentifyPacket(this, token));
            eventParser.initialize();

            synchronized(readyLock) {
                try {
                    readyLock.wait();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            logger.error("DiscordClient with the ID of " + id + " failed to connect to the Discord gateway.");
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

    public void invalidateLock() {
        if (readyLock != null) {
            synchronized(readyLock) {
                readyLock.notify();
                readyLock = null;
            }
        }
    }

    public DiscordCache getDiscordCache() {
        return discordCache;
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

    public DiscordCommandManager getGlobalCommandManager() {
        return globalCommandManager;
    }

    public Presence getPresence() {
        return presence;
    }

    public DiscordApplication getApplication() {
        return application;
    }

    public void setApplication(DiscordApplication application) {
        this.application = application;
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