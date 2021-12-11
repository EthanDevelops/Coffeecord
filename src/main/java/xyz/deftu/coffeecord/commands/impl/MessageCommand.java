package xyz.deftu.coffeecord.commands.impl;

import xyz.deftu.coffeecord.commands.ApplicationCommand;
import xyz.deftu.coffeecord.commands.ApplicationCommandType;

public class MessageCommand extends ApplicationCommand {

    public MessageCommand(long id, ApplicationCommandType type, long guildId, String name, String description, boolean defaultPermission) {
        super(id, type, guildId, name, description, defaultPermission);
    }

}