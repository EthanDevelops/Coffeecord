package xyz.deftu.blurp4j.events;

import xyz.deftu.blurp4j.DiscordClient;
import xyz.deftu.blurp4j.entities.messages.Message;
import xyz.qalcyo.json.entities.JsonElement;
import xyz.qalcyo.json.entities.JsonObject;
import xyz.qalcyo.mango.Lists;

import java.util.Collections;
import java.util.List;

public class SelectionMenuEvent extends GenericInteractionEvent {

    public final String id;
    public final List<String> values;

    public final long guildId;
    public final Message message;

    public SelectionMenuEvent(DiscordClient client, JsonObject interactionData) {
        super(client, interactionData);
        this.id = interactionData.getAsObject("data").getAsString("custom_id");
        List<String> values = Lists.newArrayList();
        for (JsonElement jsonElement : interactionData.getAsObject("data").getAsArray("values")) {
            values.add(jsonElement.getAsString());
        }
        this.values = Collections.unmodifiableList(values);

        this.guildId = interactionData.getAsLong("guild_id");
        this.message = client.getEntityCreator().createMessage(interactionData.getAsObject("message"));
    }

}