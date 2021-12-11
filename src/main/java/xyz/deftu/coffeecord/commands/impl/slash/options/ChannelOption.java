package xyz.deftu.coffeecord.commands.impl.slash.options;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import xyz.deftu.coffeecord.commands.impl.slash.SlashCommandOptionType;
import xyz.deftu.coffeecord.entities.channel.ChannelType;

import java.util.ArrayList;
import java.util.List;

public class ChannelOption extends SlashCommandOption {

    private final List<ChannelType> channelTypes = new ArrayList<>();

    public ChannelOption(String name, String description, boolean required) {
        super(SlashCommandOptionType.CHANNEL, name, description, required);
    }

    public ChannelOption(String name, String description) {
        this(name, description, false);
    }

    public List<ChannelType> getChannelTypes() {
        return channelTypes;
    }

    public ChannelOption addChannelType(ChannelType type) {
        channelTypes.add(type);
        return this;
    }

    public ChannelOption addChannelTypes(ChannelType... types) {
        for (ChannelType type : types) {
            addChannelType(type);
        }

        return this;
    }

    public ChannelOption removeChannelType(int index) {
        channelTypes.remove(index);
        return this;
    }

    public ChannelOption removeChannelType(ChannelType type) {
        channelTypes.remove(type);
        return this;
    }

    public JsonObject asJsonExt() {
        JsonObject value = new JsonObject();

        JsonArray channelTypes = new JsonArray();
        for (ChannelType channelType : this.channelTypes)
            channelTypes.add(channelType.getValue());
        value.add("channel_types", channelTypes);

        return value;
    }

}