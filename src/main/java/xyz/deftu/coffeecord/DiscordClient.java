package xyz.deftu.coffeecord;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import xyz.deftu.coffeecord.socket.DiscordSocket;
import xyz.deftu.coffeecord.socket.impl.DiscordLoginPacket;
import xyz.deftu.coffeecord.socket.GatewayIntent;

import java.net.URI;
import java.util.Collection;

public class DiscordClient extends Thread {

    public static final String NAME = "Cofeecord";

    private final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .serializeNulls()
            .setLenient()
            .create();
    private final DiscordSocket socket;

    private int intents = 0;

    public DiscordClient() {
        socket = new DiscordSocket(URI.create("wss://gateway.discord.gg/?v=9&encoding=json"), this);
    }

    public void start(String token) {
        boolean connected = socket.awaitConnect();
        if (connected) {
            socket.send(new DiscordLoginPacket(this, token));
        } else {
            throw new RuntimeException("Failed to connect to Discord gateway.");
        }
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

    public DiscordSocket getSocket() {
        return socket;
    }

    public int getIntents() {
        return intents;
    }

}