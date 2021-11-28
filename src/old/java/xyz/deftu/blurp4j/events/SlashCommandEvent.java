package xyz.deftu.blurp4j.events;

import xyz.deftu.blurp4j.commands.SlashCommand;
import xyz.deftu.blurp4j.DiscordClient;
import xyz.qalcyo.json.entities.JsonElement;
import xyz.qalcyo.json.entities.JsonObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SlashCommandEvent extends GenericInteractionEvent {

    public final String name;
    public final List<SlashCommand.SlashCommandOption> options;

    public SlashCommandEvent(DiscordClient client, JsonObject interactionData) {
        super(client, interactionData);
        this.name = interactionData.getAsObject("data").getAsString("name");
        if (interactionData.getAsObject("data").hasKey("options")) {
            List<SlashCommand.SlashCommandOption> options = new ArrayList<>();

            for (JsonElement element : interactionData.getAsObject("data").getAsArray("options")) {
                if (element.isJsonObject()) {
                    options.add(SlashCommand.SlashCommandOption.fromJson(element.getAsJsonObject()));
                }
            }

            this.options = Collections.unmodifiableList(options);
        } else {
            this.options = new ArrayList<>();
        }
    }

}