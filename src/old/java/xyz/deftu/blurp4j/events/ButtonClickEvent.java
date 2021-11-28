package xyz.deftu.blurp4j.events;

import xyz.deftu.blurp4j.DiscordClient;
import xyz.qalcyo.json.entities.JsonObject;

public class ButtonClickEvent extends GenericInteractionEvent {

    public final String customId;

    public ButtonClickEvent(DiscordClient client, JsonObject interactionData) {
        super(client, interactionData);
        this.customId = interactionData.getAsObject("data").getAsString("custom_id");
    }

}