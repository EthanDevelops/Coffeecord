package xyz.deftu.coffeecord.entities.user;

import com.google.gson.JsonObject;
import xyz.deftu.coffeecord.Coffeecord;
import xyz.deftu.coffeecord.DiscordClient;
import xyz.deftu.coffeecord.entities.JsonSerializable;
import xyz.deftu.coffeecord.entities.ISnowflake;
import xyz.deftu.coffeecord.utils.ImageFormat;

import java.util.List;

public class User implements ISnowflake, JsonSerializable<JsonObject> {

    private static final String AVATAR_URL = Coffeecord.CDN_URL + "avatars/%s/%s.%s";
    private static final String BANNER_URL = Coffeecord.CDN_URL + "banners/%s/%s.%s";

    private final DiscordClient client;

    private final long id;
    private final String username;
    private final String discriminator;
    private final String avatar;
    private final boolean bot;
    private final boolean system;
    private final String banner;
    private final List<UserFlag> flags;

    public User(DiscordClient client, long id, String username, String discriminator, String avatar, boolean bot, boolean system, String banner, int flags) {
        this.client = client;
        this.id = id;
        this.username = username;
        this.discriminator = discriminator;
        this.avatar = avatar;
        this.bot = bot;
        this.system = system;
        this.banner = banner;
        this.flags = UserFlag.from(flags);
    }

    public DiscordClient getClient() {
        return client;
    }

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getDiscriminator() {
        return discriminator;
    }

    public String asTag() {
        return username + "#" + discriminator;
    }

    public String getAvatarRaw() {
        return avatar;
    }

    public String getAvatarUrl(ImageFormat format) {
        return String.format(AVATAR_URL, id, avatar, format.getExtension());
    }

    public String getAvatarUrl() {
        return getAvatarUrl(ImageFormat.PNG);
    }

    public boolean isBot() {
        return bot;
    }

    public boolean isSystem() {
        return system;
    }

    public String getBannerRaw() {
        return banner;
    }

    public String getBannerUrl(ImageFormat format) {
        return String.format(BANNER_URL, id, banner, format.getExtension());
    }

    public String getBannerUrl() {
        return getBannerUrl(ImageFormat.PNG);
    }

    public List<UserFlag> getFlags() {
        return flags;
    }

    public JsonObject asJson() {
        JsonObject value = new JsonObject();
        return value;
    }

}