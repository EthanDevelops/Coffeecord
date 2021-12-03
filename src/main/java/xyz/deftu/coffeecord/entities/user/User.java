package xyz.deftu.coffeecord.entities.user;

import com.google.gson.JsonObject;
import org.checkerframework.common.value.qual.IntRange;
import xyz.deftu.coffeecord.Coffeecord;
import xyz.deftu.coffeecord.DiscordClient;
import xyz.deftu.coffeecord.entities.JsonSerializable;
import xyz.deftu.coffeecord.entities.ISnowflake;

import java.util.List;

public class User implements ISnowflake, JsonSerializable<JsonObject> {

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

    public String getAvatar() {
        return avatar;
    }

    public String getAvatarUrl(@IntRange(from = 64, to = 4096) int size) {
        return Coffeecord.CDN_URL + "avatars/" + id + "/" + avatar + ".png?size=" + size;
    }

    public String getAvatarUrl() {
        return getAvatarUrl(2048);
    }

    public boolean isBot() {
        return bot;
    }

    public boolean isSystem() {
        return system;
    }

    public String getBanner() {
        return banner;
    }

    public String getBannerUrl() {
        return Coffeecord.CDN_URL + "banners/" + id + "/" + banner + ".png";
    }

    public List<UserFlag> getFlags() {
        return flags;
    }

    public JsonObject asJson() {
        JsonObject value = new JsonObject();
        return value;
    }

}