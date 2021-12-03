package xyz.deftu.coffeecord.entities.message.embed;

import com.google.gson.JsonObject;
import xyz.deftu.coffeecord.entities.JsonSerializable;
import xyz.deftu.deftils.Strings;

public class MessageEmbedAuthor implements JsonSerializable<JsonObject> {

    private final String name;
    private final String url;
    private final String iconUrl;
    private final String proxyIconUrl;

    public MessageEmbedAuthor(String name, String url, String iconUrl, String proxyIconUrl) {
        this.name = name;
        this.url = url;
        this.iconUrl = iconUrl;
        this.proxyIconUrl = proxyIconUrl;
    }

    public MessageEmbedAuthor(String name, String url, String iconUrl) {
        this(name, url, iconUrl, null);
    }

    public MessageEmbedAuthor(String name, String url) {
        this(name, url, null);
    }

    public MessageEmbedAuthor(String name) {
        this(name, null);
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public String getProxyIconUrl() {
        return proxyIconUrl;
    }

    public JsonObject asJson() {
        JsonObject value = new JsonObject();

        if (Strings.isNullOrEmpty(name))
            throw new IllegalStateException("Name of an embed author cannot be null or empty.");

        value.addProperty("name", name);
        if (!Strings.isNullOrEmpty(url))
            value.addProperty("url", url);
        if (!Strings.isNullOrEmpty(iconUrl))
            value.addProperty("icon_url", iconUrl);
        if (!Strings.isNullOrEmpty(proxyIconUrl))
            value.addProperty("proxy_icon_url", proxyIconUrl);

        return value;
    }

}