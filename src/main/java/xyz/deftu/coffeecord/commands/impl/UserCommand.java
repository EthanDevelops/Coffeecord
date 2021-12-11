package xyz.deftu.coffeecord.commands.impl;

import xyz.deftu.coffeecord.commands.ApplicationCommand;
import xyz.deftu.coffeecord.commands.ApplicationCommandType;

public class UserCommand extends ApplicationCommand {

    public UserCommand(long id, ApplicationCommandType type, long guildId, String name, String description, boolean defaultPermission) {
        super(id, type, guildId, name, description, defaultPermission);
    }

}