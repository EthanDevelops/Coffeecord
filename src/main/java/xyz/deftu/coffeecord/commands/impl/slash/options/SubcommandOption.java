package xyz.deftu.coffeecord.commands.impl.slash.options;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import xyz.deftu.coffeecord.commands.impl.slash.SlashCommandOptionType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SubcommandOption extends SlashCommandOption {

    private final List<SlashCommandOption> options = new ArrayList<>();

    public SubcommandOption(String name, String description) {
        super(SlashCommandOptionType.SUBCOMMAND, name, description, false);
    }

    public List<SlashCommandOption> getOptions() {
        return Collections.unmodifiableList(options);
    }

    public SubcommandOption addOption(SlashCommandOption option) {
        options.add(option);
        return this;
    }

    public SubcommandOption addOptions(SlashCommandOption... options) {
        for (SlashCommandOption option : options) {
            addOption(option);
        }

        return this;
    }

    public SubcommandOption removeOption(int index) {
        options.remove(index);
        return this;
    }

    public JsonObject asJsonExt() {
        JsonObject value = new JsonObject();

        JsonArray options = new JsonArray();
        for (SlashCommandOption option : this.options)
            options.add(option.asJson());
        value.add("options", options);

        return value;
    }

}