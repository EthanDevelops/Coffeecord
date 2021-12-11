package xyz.deftu.coffeecord.commands.impl.slash.options;

import xyz.deftu.coffeecord.commands.impl.slash.SlashCommandOptionType;

public class GenericCommandOption extends SlashCommandOption {

    public GenericCommandOption(SlashCommandOptionType type, String name, String description, boolean required) {
        super(type, name, description, required);
    }

    public GenericCommandOption(SlashCommandOptionType type, String name, String description) {
        this(type, name, description, false);
    }

}