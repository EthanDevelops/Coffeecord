package xyz.deftu.coffeecord.events.internal.impl;

import com.google.gson.JsonObject;
import xyz.deftu.coffeecord.Coffeecord;
import xyz.deftu.coffeecord.DiscordClient;
import xyz.deftu.coffeecord.events.internal.BaseEventHandler;
import xyz.deftu.coffeecord.utils.JsonHelper;

import java.util.UUID;

public class ReadyEventHandler extends BaseEventHandler {

    public ReadyEventHandler(DiscordClient client) {
        super(client);
    }

    public void handle(JsonObject data) {
        String sessionId = JsonHelper.getString(data, "session_id");
        if (sessionId != null) { /* Shouldn't happen... */
            client.setSessionId(UUID.fromString(sessionId.replaceFirst("(\\p{XDigit}{8})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}+)", "$1-$2-$3-$4-$5")));
        }

        JsonObject userRaw = JsonHelper.getObject(data, "user");
        if (userRaw != null) { /* Shouldn't happen... */
            client.setSelfUser(client.getEntityCreator().createSelfUser(userRaw));
        }

        JsonObject applicationRaw = JsonHelper.getObject(data, "application");
        if (applicationRaw != null) { /* Shouldn't happen... */
            client.setApplication(client.getEntityCreator().createDiscordApplication(applicationRaw));
        }

        client.invalidateLock();
    }

}