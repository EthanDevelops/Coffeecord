package xyz.deftu.coffeecord.socket;

import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import org.jetbrains.annotations.Nullable;
import xyz.deftu.coffeecord.DiscordClient;

public abstract class DiscordPacket {

    protected final DiscordClient client;
    protected final DiscordPacketCode code;
    private JsonObject data = new JsonObject();
    private Object dataOverride = new DataOverride();

    private int sequenceNumber = -1;

    public DiscordPacket(DiscordClient client, DiscordPacketCode code) {
        this.client = client;
        this.code = code;
    }

    public void onSend() {
    }

    public void onReceive(JsonObject content, @Nullable JsonObject data) {
    }

    public JsonObject getData() {
        return data;
    }

    public <T> void addData(String key, T value) {
        data.add(key, client.getGson().toJsonTree(value));
    }

    public Object getDataOverride() {
        return dataOverride;
    }

    public void addDataOverride(Object dataOverride) {
        this.dataOverride = dataOverride;
    }

    public int getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(int sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    public final JsonObject asJson() {
        JsonObject value = new JsonObject();

        value.addProperty("op", code.getCode());
        if (dataOverride instanceof DataOverride) {
            if (data.size() != 0) {
                value.add("d", data);
            }
        } else {
            value.add("d", dataOverride == null ? JsonNull.INSTANCE : client.getGson().toJsonTree(dataOverride.toString()));
        }

        return value;
    }

    private static class DataOverride {
    }

}