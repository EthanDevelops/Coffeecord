package xyz.deftu.coffeecord.commands.impl.slash.options;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import xyz.deftu.coffeecord.commands.impl.slash.SlashCommandOptionType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StringOption extends SlashCommandOption {

    private final List<SlashCommandChoice<String>> choices = new ArrayList<>();

    public StringOption(String name, String description, boolean required) {
        super(SlashCommandOptionType.STRING, name, description, required);
    }

    public StringOption(String name, String description) {
        this(name, description, false);
    }

    public List<SlashCommandChoice<String>> getChoices() {
        return Collections.unmodifiableList(choices);
    }

    public StringOption addChoice(SlashCommandChoice<String> choice) {
        choices.add(choice);
        return this;
    }

    public StringOption addChoice(String name, String value) {
        return addChoice(new SlashCommandChoice<>(name, value));
    }

    public StringOption addChoices(SlashCommandChoice<String>... choices) {
        for (SlashCommandChoice<String> choice : choices) {
            addChoice(choice);
        }

        return this;
    }

    public StringOption removeChoice(int index) {
        choices.remove(index);
        return this;
    }

    public JsonObject asJsonExt() {
        JsonObject value = new JsonObject();

        JsonArray choices = new JsonArray();
        for (SlashCommandChoice<String> choice : this.choices)
            choices.add(choice.asJson());
        value.add("choices", choices);

        return value;
    }

}