package xyz.deftu.coffeecord.entities;

import xyz.deftu.coffeecord.entities.channel.BaseChannel;
import xyz.deftu.coffeecord.entities.guild.Guild;
import xyz.deftu.coffeecord.entities.user.User;

import java.util.HashMap;
import java.util.Map;

public class EntityCache {

    public final Map<Long, User> userCache = new HashMap<>();
    public final Map<Long, Guild> guildCache = new HashMap<>();
    public final Map<Long, BaseChannel> channelCache = new HashMap<>();

}