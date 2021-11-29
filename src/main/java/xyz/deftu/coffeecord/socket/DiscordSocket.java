package xyz.deftu.coffeecord.socket;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import xyz.deftu.coffeecord.Coffeecord;
import xyz.deftu.coffeecord.DiscordClient;
import xyz.deftu.coffeecord.socket.impl.DiscordDispatchPacket;
import xyz.deftu.coffeecord.socket.impl.DiscordHeartbeatPacket;
import xyz.deftu.coffeecord.socket.impl.DiscordHelloPacket;
import xyz.deftu.coffeecord.socket.impl.DiscordLoginPacket;
import xyz.deftu.coffeecord.utils.JsonHelper;

import java.lang.reflect.Constructor;
import java.net.URI;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class DiscordSocket extends WebSocketClient {

    private final DiscordClient client;
    private final Logger logger = Coffeecord.createLogger(DiscordSocket.class);

    private final Map<DiscordPacketCode, Class<? extends DiscordPacket>> packetRegistry = new HashMap<>();

    public DiscordSocket(URI serverUri, DiscordClient client) {
        super(serverUri);
        this.client = client;

        initialize();
    }

    public void initialize() {
        /* Connection. */
        addPacket(DiscordPacketCode.IDENTIFY, DiscordLoginPacket.class);
        addPacket(DiscordPacketCode.HELLO, DiscordHelloPacket.class);
        addPacket(DiscordPacketCode.HEARTBEAT, DiscordHeartbeatPacket.class);

        /* Communication/interaction. */
        addPacket(DiscordPacketCode.DISPATCH, DiscordDispatchPacket.class);
    }

    public boolean awaitConnect() {
        try {
            return connectBlocking(30, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void onOpen(ServerHandshake handshake) {
        logger.info("Opened connection with Discord gateway.");
    }

    public void onMessage(String message) {
        if (Coffeecord.isSocketDebug()) {
            logger.info("Received message from Discord gateway: {}", message);
        }

        if (JsonHelper.isValid(message)) {
            JsonElement element = JsonParser.parseString(message);
            if (element.isJsonObject()) {
                JsonObject object = element.getAsJsonObject();
                if (object.has("op")) {
                    int op = object.get("op").getAsInt();
                    Class<? extends DiscordPacket> clazz = packetRegistry.get(DiscordPacketCode.from(op));
                    if (clazz != null) {
                        try {
                            Constructor<? extends DiscordPacket> constructor = clazz.getConstructor(DiscordClient.class);
                            constructor.setAccessible(true);
                            DiscordPacket packet = constructor.newInstance(client);
                            if (object.has("s")) {
                                JsonElement sequenceNumber = object.get("s");
                                if (!sequenceNumber.isJsonNull()) {
                                    packet.setSequenceNumber(sequenceNumber.getAsInt());
                                }
                            }

                            packet.onReceive(object, object.getAsJsonObject("d"));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    public void onClose(int code, String reason, boolean remote) {
        logger.warn("Connection to Discord gateway closed. (code: {} - reason: {})", code, reason);
    }

    public void onError(Exception ex) {
        logger.error("An unexpected error occurred.", ex);
    }

    public void send(DiscordPacket packet) {
        packet.onSend();
        String content = client.getGson().toJson(packet.asJson());
        if (Coffeecord.isSocketDebug()) {
            logger.info("Sent packet: {}", content);
        }

        send(content);
    }

    public void addPacket(DiscordPacketCode code, Class<? extends DiscordPacket> packet) {
        packetRegistry.put(code, packet);
    }

    public DiscordClient getClient() {
        return client;
    }

    public Map<DiscordPacketCode, Class<? extends DiscordPacket>> getPacketRegistry() {
        return Collections.unmodifiableMap(packetRegistry);
    }

}