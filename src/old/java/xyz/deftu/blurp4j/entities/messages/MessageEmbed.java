package xyz.deftu.blurp4j.entities.messages;

import xyz.deftu.blurp4j.interfaces.IBuilder;
import xyz.deftu.blurp4j.interfaces.IJsonifiable;
import xyz.qalcyo.json.entities.JsonObject;
import xyz.qalcyo.mango.Numbers;
import xyz.qalcyo.mango.Strings;

public class MessageEmbed implements IJsonifiable<JsonObject> {

    private final String title, description, url, colour;
    private final EmbedFooter footer;
    private final EmbedImage image;

    public MessageEmbed(String title, String description, String url, String colour, EmbedFooter footer, EmbedImage image) {
        this.title = title;
        this.description = description;
        this.url = url;
        this.colour = colour;
        this.footer = footer;
        this.image = image;
    }

    public static MessageEmbedBuilder newBuilder() {
        return new MessageEmbedBuilder();
    }

    public static EmbedFooter newFooter(String text, String icon) {
        return new EmbedFooter(text, icon);
    }

    public static EmbedFooter newFooter(String text) {
        return new EmbedFooter(text);
    }

    public static EmbedImage newImage(String url, int width, int height) {
        return new EmbedImage(url, width, height);
    }

    public static EmbedImage newImage(String url) {
        return new EmbedImage(url);
    }

    public JsonObject jsonify() {
        JsonObject value = new JsonObject();

        if (!Strings.isNullOrEmpty(title))
            value.add("title", title);
        if (!Strings.isNullOrEmpty(description))
            value.add("description", description);
        if (footer != null)
            value.add("footer", footer.jsonify());
        if (image != null)
            value.add("image", image.jsonify());

        return value;
    }

    private static class EmbedFooter implements IJsonifiable<JsonObject> {

        private String text;
        private String icon;

        public EmbedFooter(String text, String icon) {
            this.text = text;
            this.icon = icon;
        }

        public EmbedFooter(String text) {
            this(text, null);
        }

        public String text() {
            return text;
        }

        public EmbedFooter text(String text) {
            this.text = text;
            return this;
        }

        public String icon() {
            return icon;
        }

        public EmbedFooter icon(String icon) {
            this.icon = icon;
            return this;
        }

        public JsonObject jsonify() {
            JsonObject value = new JsonObject();

            if (Strings.isNullOrEmpty(text))
                throw new NullPointerException("Text cannot be null or empty!");

            value.add("text", text);
            if (!Strings.isNullOrEmpty(icon))
                value.add("icon_url", icon);

            return value;
        }

    }

    private static class EmbedImage implements IJsonifiable<JsonObject> {

        private String url;
        private int width, height;

        public EmbedImage(String url, int width, int height) {
            this.url = url;
            this.width = width;
            this.height = height;
        }

        public EmbedImage(String url) {
            this(url, -1, -1);
        }

        public String url() {
            return url;
        }

        public EmbedImage url(String url) {
            this.url = url;
            return this;
        }

        public int width() {
            return width;
        }

        public EmbedImage width(int width) {
            this.width = width;
            return this;
        }

        public int height() {
            return height;
        }

        public EmbedImage height(int height) {
            this.height = height;
            return this;
        }

        public JsonObject jsonify() {
            JsonObject value = new JsonObject();

            if (!Strings.isNullOrEmpty(url))
                value.add("url", url);
            if (!Numbers.isNegative(width))
                value.add("width", width);
            if (!Numbers.isNegative(height))
                value.add("height", height);

            return value;
        }

    }

    public static class MessageEmbedBuilder implements IBuilder<MessageEmbed> {

        private String title, description, url, colour;
        private EmbedFooter footer;
        private EmbedImage image;

        public String title() {
            return title;
        }

        public MessageEmbedBuilder title(String title) {
            this.title = title;
            return this;
        }

        public String description() {
            return description;
        }

        public MessageEmbedBuilder description(String description) {
            this.description = description;
            return this;
        }

        public String url() {
            return url;
        }

        public MessageEmbedBuilder url(String url) {
            this.url = url;
            return this;
        }

        public String colour() {
            return colour;
        }

        public MessageEmbedBuilder colour(String colour) {
            this.colour = colour;
            return this;
        }

        public EmbedFooter footer() {
            return footer;
        }

        public MessageEmbedBuilder footer(EmbedFooter footer) {
            this.footer = footer;
            return this;
        }

        public EmbedImage image() {
            return image;
        }

        public MessageEmbedBuilder image(EmbedImage image) {
            this.image = image;
            return this;
        }

        public MessageEmbed build() {
            return new MessageEmbed(title, description, url, colour, footer, image);
        }

    }

}