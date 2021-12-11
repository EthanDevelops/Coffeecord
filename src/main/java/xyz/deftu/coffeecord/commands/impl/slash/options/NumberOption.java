package xyz.deftu.coffeecord.commands.impl.slash.options;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import xyz.deftu.coffeecord.commands.impl.slash.SlashCommandOptionType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NumberOption extends SlashCommandOption {

    private final List<SlashCommandChoice<Number>> choices = new ArrayList<>();

    public NumberOption(String name, String description, boolean required) {
        super(SlashCommandOptionType.INTEGER, name, description, required);
    }

    public NumberOption(String name, String description) {
        this(name, description, false);
    }

    public List<SlashCommandChoice<Number>> getChoices() {
        return Collections.unmodifiableList(choices);
    }

    public NumberOption addChoice(SlashCommandChoice<Number> choice) {
        choices.add(choice);
        return this;
    }

    public NumberOption addChoice(String name, int value) {
        return addChoice(new SlashCommandChoice<>(name, value));
    }

    public NumberOption addChoices(SlashCommandChoice<Number>... choices) {
        for (SlashCommandChoice<Number> choice : choices) {
            addChoice(choice);
        }

        return this;
    }

    public NumberOption removeChoice(int index) {
        choices.remove(index);
        return this;
    }

    public JsonObject asJsonExt() {
        JsonObject value = new JsonObject();

        JsonArray choices = new JsonArray();
        for (SlashCommandChoice<Number> choice : this.choices)
            choices.add(choice.asJson());
        value.add("choices", choices);

        return value;
    }

}