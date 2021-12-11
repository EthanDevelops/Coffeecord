package xyz.deftu.coffeecord.commands.impl.slash;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import xyz.deftu.coffeecord.commands.ApplicationCommand;
import xyz.deftu.coffeecord.commands.ApplicationCommandType;
import xyz.deftu.coffeecord.entities.guild.Guild;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SlashCommand extends ApplicationCommand {

    public static final Pattern NAME_REGEX = Pattern.compile("^[\\w-]{1,32}$");

    private final List<SlashCommandOption> options = new ArrayList<>();

    public SlashCommand(Guild guild, String name, String description, boolean defaultPermission) {
        super(-1, ApplicationCommandType.CHAT, guild == null ? -1 : guild.getId(), checkName(name), description, defaultPermission);
    }

    public SlashCommand(String name, String description, boolean defaultPermission) {
        this(null, name, description, defaultPermission);
    }

    public SlashCommand(Guild guild, String name, String description) {
        this(guild, name, description, true);
    }

    public SlashCommand(String name, String description) {
        this(null, name, description, true);
    }

    public List<SlashCommandOption> getOptions() {
        return Collections.unmodifiableList(options);
    }

    public SlashCommand addOption(SlashCommandOption option) {
        options.add(option);
        return this;
    }

    public SlashCommand removeOption(int index) {
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

    public static String checkName(String name) {
        Matcher matcher = NAME_REGEX.matcher(name);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Name provided for slash command doesn't follow Discord's name pattern.");
        }

        return name;
    }

}