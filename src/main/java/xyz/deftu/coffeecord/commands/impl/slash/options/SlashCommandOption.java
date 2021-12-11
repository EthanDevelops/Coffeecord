package xyz.deftu.coffeecord.commands.impl.slash.options;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import xyz.deftu.coffeecord.commands.impl.slash.SlashCommandOptionType;
import xyz.deftu.coffeecord.entities.JsonSerializable;
import xyz.deftu.coffeecord.utils.JsonSerializableHelper;

import java.util.Map;

public abstract class SlashCommandOption implements JsonSerializable<JsonObject> {

    private final SlashCommandOptionType type;
    private final String name;
    private final String description;
    private final boolean required;

    public SlashCommandOption(SlashCommandOptionType type, String name, String description, boolean required) {
        this.type = type;
        this.name = name;
        this.description = description;
        this.required = required;
    }

    public JsonObject asJsonExt() {
        return new JsonObject();
    }

    public final JsonObject asJson() {
        JsonObject value = new JsonObject();

        value.addProperty("type", JsonSerializableHelper.check("Slash command option", "type", type).getValue());
        value.addProperty("name", JsonSerializableHelper.check("Slash command option", "name", name));
        value.addProperty("description", JsonSerializableHelper.check("Slash command option", "description", description));
        if (required) {
            value.addProperty("required", required);
        }

        JsonObject ext = asJsonExt();
        if (ext != null) {
            for (Map.Entry<String, JsonElement> entry : ext.entrySet()) {
                value.add(entry.getKey(), entry.getValue());
            }
        }

        return value;
    }

    public static SubcommandOption subcommand(String name, String description) {
        return new SubcommandOption(name, description);
    }

    public static SubcommandGroupOption subcommandGroup(String name, String description) {
        return new SubcommandGroupOption(name, description);
    }

    public static StringOption string(String name, String description, boolean required) {
        return new StringOption(name, description, required);
    }

    public static StringOption string(String name, String description) {
        return new StringOption(name, description);
    }

    public static IntegerOption integer(String name, String description, boolean required) {
        return new IntegerOption(name, description, required);
    }

    public static IntegerOption integer(String name, String description) {
        return new IntegerOption(name, description);
    }

    public static GenericCommandOption bool(String name, String description, boolean required) {
        return new GenericCommandOption(SlashCommandOptionType.BOOLEAN, name, description, required);
    }

    public static GenericCommandOption bool(String name, String description) {
        return new GenericCommandOption(SlashCommandOptionType.BOOLEAN, name, description, false);
    }

    public static GenericCommandOption user(String name, String description, boolean required) {
        return new GenericCommandOption(SlashCommandOptionType.USER, name, description, required);
    }

    public static GenericCommandOption user(String name, String description) {
        return new GenericCommandOption(SlashCommandOptionType.USER, name, description, false);
    }

    public static ChannelOption channel(String name, String description, boolean required) {
        return new ChannelOption(name, description, required);
    }

    public static ChannelOption channel(String name, String description) {
        return new ChannelOption(name, description);
    }

    public static GenericCommandOption role(String name, String description, boolean required) {
        return new GenericCommandOption(SlashCommandOptionType.ROLE, name, description, required);
    }

    public static GenericCommandOption role(String name, String description) {
        return new GenericCommandOption(SlashCommandOptionType.ROLE, name, description, false);
    }

    public static GenericCommandOption mentionable(String name, String description, boolean required) {
        return new GenericCommandOption(SlashCommandOptionType.MENTIONABLE, name, description, required);
    }

    public static GenericCommandOption mentionable(String name, String description) {
        return new GenericCommandOption(SlashCommandOptionType.MENTIONABLE, name, description, false);
    }

    public static NumberOption number(String name, String description, boolean required) {
        return new NumberOption(name, description, required);
    }

    public static NumberOption number(String name, String description) {
        return new NumberOption(name, description);
    }

}