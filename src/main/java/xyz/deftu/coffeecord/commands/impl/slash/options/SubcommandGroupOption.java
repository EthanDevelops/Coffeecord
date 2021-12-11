package xyz.deftu.coffeecord.commands.impl.slash.options;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import xyz.deftu.coffeecord.commands.impl.slash.SlashCommandOptionType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SubcommandGroupOption extends SlashCommandOption {

    private final List<SubcommandOption> subcommands = new ArrayList<>();

    public SubcommandGroupOption(String name, String description) {
        super(SlashCommandOptionType.SUBCOMMAND_GROUP, name, description, false);
    }

    public List<SubcommandOption> getSubcommands() {
        return Collections.unmodifiableList(subcommands);
    }

    public SubcommandGroupOption addSubcommand(SubcommandOption option) {
        subcommands.add(option);
        return this;
    }

    public SubcommandGroupOption addSubcommands(SubcommandOption... options) {
        for (SubcommandOption subcommand : subcommands) {
            addSubcommand(subcommand);
        }

        return this;
    }

    public SubcommandGroupOption removeOption(int index) {
        subcommands.remove(index);
        return this;
    }

    public JsonObject asJsonExt() {
        JsonObject value = new JsonObject();

        JsonArray subcommands = new JsonArray();
        for (SlashCommandOption subcommand : this.subcommands)
            subcommands.add(subcommand.asJson());
        value.add("options", subcommands);

        return value;
    }

}