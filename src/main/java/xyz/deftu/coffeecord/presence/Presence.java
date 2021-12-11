package xyz.deftu.coffeecord.presence;

import com.google.gson.JsonArray;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import xyz.deftu.coffeecord.DiscordClient;
import xyz.deftu.coffeecord.entities.JsonSerializable;
import xyz.deftu.coffeecord.socket.impl.DiscordPresenceUpdatePacket;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Presence implements JsonSerializable<JsonObject> {

    private final DiscordClient client;
    private OnlineStatus onlineStatus = OnlineStatus.ONLINE;
    private List<Activity> activities = new ArrayList<>();

    public Presence(DiscordClient client) {
        this.client = client;
    }

    public Presence update() {
        client.getSocket().send(new DiscordPresenceUpdatePacket(client));
        return this;
    }

    public OnlineStatus getOnlineStatus() {
        return onlineStatus;
    }

    public Presence setOnlineStatus(OnlineStatus onlineStatus) {
        this.onlineStatus = onlineStatus;
        return this;
    }

    public List<Activity> getActivities() {
        return Collections.unmodifiableList(activities);
    }

    public Presence setActivities(List<Activity> activities) {
        this.activities = activities;
        return this;
    }

    public Presence addActivity(Activity activity) {
        activities.add(activity);
        return this;
    }

    public Presence removeActivity(int index) {
        activities.remove(index);
        return this;
    }

    public Presence clearActivities() {
        activities.clear();
        return this;
    }

    public Presence reset() {
        setOnlineStatus(OnlineStatus.ONLINE);
        clearActivities();
        return this;
    }

    public JsonObject asJson() {
        JsonObject value = new JsonObject();

        if (onlineStatus == null)
            throw new IllegalStateException("Presence online status cannot be null.");
        if (activities == null)
            throw new IllegalStateException("Presence activities cannot be null.");

        value.addProperty("status", onlineStatus.getValue());
        JsonArray activities = new JsonArray();
        for (Activity activity : this.activities)
            activities.add(activity.asJson());
        value.add("activities", activities);
        value.addProperty("afk", false);
        value.add("since", JsonNull.INSTANCE);

        return value;
    }

}