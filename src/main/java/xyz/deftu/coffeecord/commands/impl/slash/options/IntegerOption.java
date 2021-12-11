package xyz.deftu.coffeecord.commands.impl.slash.options;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import xyz.deftu.coffeecord.commands.impl.slash.SlashCommandOptionType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class IntegerOption extends SlashCommandOption {

    private final List<SlashCommandChoice<Integer>> choices = new ArrayList<>();

    public IntegerOption(String name, String description, boolean required) {
        super(SlashCommandOptionType.INTEGER, name, description, required);
    }

    public IntegerOption(String name, String description) {
        this(name, description, false);
    }

    public List<SlashCommandChoice<Integer>> getChoices() {
        return Collections.unmodifiableList(choices);
    }

    public IntegerOption addChoice(SlashCommandChoice<Integer> choice) {
        choices.add(choice);
        return this;
    }

    public IntegerOption addChoice(String name, int value) {
        return addChoice(new SlashCommandChoice<>(name, value));
    }

    public IntegerOption addChoices(SlashCommandChoice<Integer>... choices) {
        for (SlashCommandChoice<Integer> choice : choices) {
            addChoice(choice);
        }

        return this;
    }

    public IntegerOption removeChoice(int index) {
        choices.remove(index);
        return this;
    }

    public JsonObject asJsonExt() {
        JsonObject value = new JsonObject();

        JsonArray choices = new JsonArray();
        for (SlashCommandChoice<Integer> choice : this.choices)
            choices.add(choice.asJson());
        value.add("choices", choices);

        return value;
    }

}