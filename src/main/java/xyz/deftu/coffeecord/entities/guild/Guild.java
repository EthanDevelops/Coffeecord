package xyz.deftu.coffeecord.entities.guild;

import xyz.deftu.coffeecord.DiscordClient;
import xyz.deftu.coffeecord.entities.ISnowflake;
import xyz.deftu.coffeecord.requests.types.GuildRegionRequest;
import xyz.deftu.coffeecord.utils.ImageFormat;

import java.util.List;

public class Guild implements ISnowflake {

    private static String ICON_URL = "https://cdn.discordapp.com/icons/%s/%s.%s";

    private final DiscordClient client;
    private final long id;

    private final String name;
    private final String icon;
    private final boolean owner;
    private final List<GuildPermission> permissions;
    private final List<GuildFeature> features;

    public Guild(DiscordClient client, long id, String name, String icon, boolean owner, List<GuildPermission> permissions, List<GuildFeature> features) {
        this.client = client;
        this.id = id;
        this.name = name;
        this.icon = icon;
        this.owner = owner;
        this.permissions = permissions;
        this.features = features;
    }

    public DiscordClient getClient() {
        return client;
    }

    public long getId() {
        return id;
    }

    /* Slash commands. */

    public List<Region> retrieveRegions(boolean deprecated) {
        return client.getRestRequester().request(new GuildRegionRequest(client, this, deprecated));
    }

    public List<Region> retrieveRegions() {
        return retrieveRegions(false);
    }

    public String getName() {
        return name;
    }

    public String getIconRaw() {
        return icon;
    }

    public String getIconUrl(ImageFormat format) {
        return String.format(ICON_URL, getId(), getIconRaw(), format.getExtension());
    }

    public String getIconUrl() {
        return getIconUrl(ImageFormat.PNG);
    }

    public boolean isOwner() {
        return owner;
    }

    public List<GuildPermission> getPermissions() {
        return permissions;
    }

    public List<GuildFeature> getFeatures() {
        return features;
    }

}