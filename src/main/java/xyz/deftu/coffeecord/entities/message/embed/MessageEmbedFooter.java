package xyz.deftu.coffeecord.entities.message.embed;

import com.google.gson.JsonObject;
import xyz.deftu.coffeecord.entities.JsonSerializable;
import xyz.deftu.deftils.Strings;

public class MessageEmbedFooter implements JsonSerializable<JsonObject> {

    private final String text;
    private final String icon;
    private final String proxyIcon;

    public MessageEmbedFooter(String text, String icon, String proxyIcon) {
        this.text = text;
        this.icon = icon;
        this.proxyIcon = proxyIcon;
    }

    public MessageEmbedFooter(String text, String icon) {
        this(text, icon, null);
    }

    public MessageEmbedFooter(String text) {
        this(text, null);
    }

    public JsonObject asJson() {
        JsonObject value = new JsonObject();

        if (Strings.isNullOrEmpty(text))
            throw new IllegalStateException("Text of an embed footer cannot be null or empty.");

        value.addProperty("text", text);
        if (!Strings.isNullOrEmpty(icon))
            value.addProperty("icon_url", icon);
        if (!Strings.isNullOrEmpty(proxyIcon))
            value.addProperty("proxy_icon_url", proxyIcon);

        return value;
    }

}