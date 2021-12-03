package xyz.deftu.coffeecord.entities.message.embed;

import com.google.gson.JsonObject;
import xyz.deftu.coffeecord.entities.JsonSerializable;
import xyz.deftu.deftils.Strings;

public class MessageEmbedMedia implements JsonSerializable<JsonObject> {

    private final String url;
    private final String proxyUrl;
    private final int width, height;

    public MessageEmbedMedia(String url, String proxyUrl, int width, int height) {
        this.url = url;
        this.proxyUrl = proxyUrl;
        this.width = width;
        this.height = height;
    }

    public MessageEmbedMedia(String url, String proxyUrl) {
        this(url, proxyUrl, -1, -1);
    }

    public MessageEmbedMedia(String url) {
        this(url, null);
    }

    public String getUrl() {
        return url;
    }

    public String getProxyUrl() {
        return proxyUrl;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public JsonObject asJson() {
        JsonObject value = new JsonObject();

        if (Strings.isNullOrEmpty(url))
            throw new IllegalStateException("URL of an embed image cannot be null or empty.");

        value.addProperty("url", url);
        if (!Strings.isNullOrEmpty(proxyUrl))
            value.addProperty("proxy_url", proxyUrl);
        if (width > -1)
            value.addProperty("width", width);
        if (height > -1)
            value.addProperty("height", height);

        return value;
    }

}