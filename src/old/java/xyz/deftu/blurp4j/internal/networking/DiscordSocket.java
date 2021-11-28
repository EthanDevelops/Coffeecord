package xyz.deftu.blurp4j.internal.networking;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;
import xyz.deftu.blurp4j.DiscordClient;
import xyz.deftu.blurp4j.internal.networking.handlers.InteractionCreateHandler;
import xyz.deftu.blurp4j.internal.networking.handlers.MessageCreateHandler;
import xyz.qalcyo.json.entities.JsonObject;
import xyz.qalcyo.json.parser.JsonParser;
import xyz.qalcyo.json.util.JsonHelper;
import xyz.qalcyo.mango.Multithreading;
import xyz.qalcyo.mango.functions.QuadConsumer;

import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;

public final class DiscordSocket extends WebSocketClient {

    private final DiscordClient client;
    private final Logger logger;

    private boolean initialized = false;
    private final Map<String, DiscordSocketHandler> handlers;

    private final List<BiConsumer<DiscordSocket, ServerHandshake>> openListeners = new ArrayList<>();
    private final List<QuadConsumer<DiscordSocket, Integer, String, Boolean>> closeListeners = new ArrayList<>();
    private final List<BiConsumer<DiscordSocket, Exception>> errorListeners = new ArrayList<>();

    public DiscordSocket(URI uri, DiscordClient client) {
        super(uri, new Draft_6455());
        this.client = client;
        this.logger = LogManager.getLogger("Java4Discord (ClientSocket)");

        this.handlers = new HashMap<>();
    }

    public void onOpen(ServerHandshake handshake) {
        logger.info("Successfully connected to the Discord websocket.");

        for (BiConsumer<DiscordSocket, ServerHandshake> openListener : openListeners) {
            openListener.accept(this, handshake);
        }
    }

    public void onMessage(String message) {
        handle(message);
    }

    public void onMessage(ByteBuffer bytes) {
        handle(StandardCharsets.UTF_8.decode(bytes).toString());
    }

    public void onClose(int code, String reason, boolean remote) {
        logger.warn("Connection to the Discord websocket was closed. (" + code + " | " + reason + ")");

        for (QuadConsumer<DiscordSocket, Integer, String, Boolean> closeListener : closeListeners) {
            closeListener.accept(this, code, reason, remote);
        }
    }

    public void onError(Exception ex) {
        logger.error("An unexpected error occurred in the Discord websocket.", ex);

        for (BiConsumer<DiscordSocket, Exception> errorListener : errorListeners) {
            errorListener.accept(this, ex);
        }
    }

    /* Handling. */

    private void handle(String message) {
        logger.trace(message);
        if (message.startsWith("{") && message.endsWith("}")) {
            JsonObject object = JsonParser.parse(message).getAsJsonObject();

            if (object.hasKey("t") && object.hasKey("op") && (object.hasKey("d") || object.hasKey("data")) && object.get("d").isJsonObject()) {
                DiscordSocketCode op = DiscordSocketCode.fromOp(object.getAsInt("op"));
                JsonObject data = object.hasKey("d") ? object.getAsObject("d") : object.has("data") ? object.getAsObject("data") : null;

                if (data == null) {
                    logger.warn("Encountered null data. ({})", op);
                    return;
                }

                if (op == null) {
                    logger.warn("Encountered null opcode. ({})", object.getAsInt("op"));
                    return;
                }

                if (Boolean.parseBoolean(System.getProperty("java4discord.debug", "false"))) {
                    logger.warn(JsonHelper.makePretty(object));
                }

                switch (op) {
                    case DISPATCH:
                        dispatch(object.getAsString("t"), object);
                        break;
                    case HELLO:
                        activeHeartbeating(data.getAsInt("heartbeat_interval"));
                        break;
                    case HEARTBEAT_ACK:
                        logger.trace("Received heartbeat ack. (11)");
                        break;
                }
            }
        }
    }

    private void dispatch(String type, JsonObject object) {
        if (!handlers.containsKey(type))
            return;
        DiscordSocketHandler handler = handlers.get(type);
        handler.handle(object);
    }

    /* Messaging. */

    public void send(byte[] data) {
        try {
            if (!isOpen())
                reconnectBlocking();
            super.send(data);
        } catch (Exception e) {
            onError(e);
        }
    }

    public void send(ByteBuffer bytes) {
        try {
            if (!isOpen())
                reconnectBlocking();
            super.send(bytes);
        } catch (Exception e) {
            onError(e);
        }
    }

    public void send(String text) {
        send(StandardCharsets.UTF_8.encode(text));
    }

    public void send(JsonObject object) {
        send(object.getAsString());
    }

    /* Listener handling. */

    public void addOpenListener(BiConsumer<DiscordSocket, ServerHandshake> openListener) {
        openListeners.add(openListener);
    }

    public void addCloseListener(QuadConsumer<DiscordSocket, Integer, String, Boolean> closeListener) {
        closeListeners.add(closeListener);
    }

    public void addErrorListener(BiConsumer<DiscordSocket, Exception> errorListener) {
        errorListeners.add(errorListener);
    }

    /* Internal. */

    public void initialize() {
        if (initialized)
            throw new IllegalStateException("Can't initialize twice.");

        handlers.put("MESSAGE_CREATE", new MessageCreateHandler(this, client));
        handlers.put("INTERACTION_CREATE", new InteractionCreateHandler(this, client));

        initialized = true;
    }

    void activeHeartbeating(int interval) {
        Multithreading.schedule(() -> send(new JsonObject().add("op", DiscordSocketCode.HEARTBEAT.getDispatch()).add("d", 251)), 5, interval, TimeUnit.MILLISECONDS);
    }

}