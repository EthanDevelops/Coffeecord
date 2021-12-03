package xyz.deftu.coffeecord.entities.message.embed;

import java.awt.*;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MessageEmbedBuilder {

    private String title;
    private String description;
    private String url;
    private OffsetDateTime timestamp;
    private int colour;
    private MessageEmbedFooter footer;
    private MessageEmbedMedia image;
    private MessageEmbedMedia thumbnail;
    private MessageEmbedMedia video;
    private MessageEmbedAuthor author;
    private List<MessageEmbedField> fields = new ArrayList<>();

    public String getTitle() {
        return title;
    }

    public MessageEmbedBuilder setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public MessageEmbedBuilder setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public MessageEmbedBuilder setUrl(String url) {
        this.url = url;
        return this;
    }

    public OffsetDateTime getTimestamp() {
        return timestamp;
    }

    public MessageEmbedBuilder setTimestamp(OffsetDateTime timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public int getColour() {
        return colour;
    }

    public MessageEmbedBuilder setColour(Color colour) {
        this.colour = colour.getRGB();
        return this;
    }

    public MessageEmbedBuilder setColourRaw(int colour) {
        this.colour = colour;
        return this;
    }

    public MessageEmbedFooter getFooter() {
        return footer;
    }

    public MessageEmbedBuilder setFooter(MessageEmbedFooter footer) {
        this.footer = footer;
        return this;
    }

    public MessageEmbedBuilder setFooter(String text, String icon, String proxyIcon) {
        return setFooter(new MessageEmbedFooter(text, icon, proxyIcon));
    }

    public MessageEmbedBuilder setFooter(String text, String icon) {
        return setFooter(new MessageEmbedFooter(text, icon));
    }

    public MessageEmbedBuilder setFooter(String text) {
        return setFooter(new MessageEmbedFooter(text));
    }

    public MessageEmbedMedia getImage() {
        return image;
    }

    public MessageEmbedBuilder setImage(MessageEmbedMedia image) {
        this.image = image;
        return this;
    }

    public MessageEmbedBuilder setImage(String url, String proxyUrl, int width, int height) {
        return setImage(new MessageEmbedMedia(url, proxyUrl, width, height));
    }

    public MessageEmbedBuilder setImage(String url, String proxyUrl) {
        return setImage(new MessageEmbedMedia(url, proxyUrl));
    }

    public MessageEmbedBuilder setImage(String url) {
        return setImage(new MessageEmbedMedia(url));
    }

    public MessageEmbedMedia getThumbnail() {
        return thumbnail;
    }

    public MessageEmbedBuilder setThumbnail(MessageEmbedMedia thumbnail) {
        this.thumbnail = thumbnail;
        return this;
    }

    public MessageEmbedBuilder setThumbnail(String url, String proxyUrl, int width, int height) {
        return setThumbnail(new MessageEmbedMedia(url, proxyUrl, width, height));
    }

    public MessageEmbedBuilder setThumbnail(String url, String proxyUrl) {
        return setThumbnail(new MessageEmbedMedia(url, proxyUrl));
    }

    public MessageEmbedBuilder setThumbnail(String url) {
        return setThumbnail(new MessageEmbedMedia(url));
    }

    public MessageEmbedMedia getVideo() {
        return video;
    }

    public MessageEmbedBuilder setVideo(MessageEmbedMedia video) {
        this.video = video;
        return this;
    }

    public MessageEmbedBuilder setVideo(String url, String proxyUrl, int width, int height) {
        return setVideo(new MessageEmbedMedia(url, proxyUrl, width, height));
    }

    public MessageEmbedBuilder setVideo(String url, String proxyUrl) {
        return setVideo(new MessageEmbedMedia(url, proxyUrl));
    }

    public MessageEmbedBuilder setVideo(String url) {
        return setVideo(new MessageEmbedMedia(url));
    }

    public MessageEmbedAuthor getAuthor() {
        return author;
    }

    public MessageEmbedBuilder setAuthor(MessageEmbedAuthor author) {
        this.author = author;
        return this;
    }

    public MessageEmbedBuilder setAuthor(String name, String url, String iconUrl, String proxyIconUrl) {
        return setAuthor(new MessageEmbedAuthor(name, url, iconUrl, proxyIconUrl));
    }

    public MessageEmbedBuilder setAuthor(String name, String url, String iconUrl) {
        return setAuthor(new MessageEmbedAuthor(name, url, iconUrl));
    }

    public MessageEmbedBuilder setAuthor(String name, String url) {
        return setAuthor(new MessageEmbedAuthor(name, url));
    }

    public MessageEmbedBuilder setAuthor(String name) {
        return setAuthor(new MessageEmbedAuthor(name));
    }

    public List<MessageEmbedField> getFields() {
        return fields;
    }

    public MessageEmbedBuilder setFields(List<MessageEmbedField> fields) {
        this.fields = fields;
        return this;
    }

    public MessageEmbedBuilder setFields(MessageEmbedField... fields) {
        return setFields(Arrays.asList(fields));
    }

    public MessageEmbedBuilder addField(MessageEmbedField field) {
        fields.add(field);
        return this;
    }

    public MessageEmbedBuilder addField(String name, String value, boolean inline) {
        return addField(new MessageEmbedField(name, value, inline));
    }

    public MessageEmbedBuilder addField(String name, String value) {
        return addField(new MessageEmbedField(name, value));
    }

    public MessageEmbedBuilder removeField(int index) {
        fields.remove(index);
        return this;
    }

    public MessageEmbed build() {
        return new MessageEmbed(title, MessageEmbedType.RICH, description, url, timestamp, colour, footer, image, thumbnail, video, author, fields);
    }

}