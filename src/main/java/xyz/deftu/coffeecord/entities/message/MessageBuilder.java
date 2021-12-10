package xyz.deftu.coffeecord.entities.message;

import xyz.deftu.coffeecord.entities.message.embed.MessageEmbed;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MessageBuilder {

    private boolean tts;
    private List<MessageEmbed> embeds = new ArrayList<>();
    private String content;

    public boolean isTts() {
        return tts;
    }

    public MessageBuilder setTts(boolean tts) {
        this.tts = tts;
        return this;
    }

    public List<MessageEmbed> getEmbeds() {
        return embeds;
    }

    public MessageBuilder setEmbeds(List<MessageEmbed> embeds) {
        this.embeds = embeds;
        return this;
    }

    public MessageBuilder setEmbeds(MessageEmbed... embeds) {
        return setEmbeds(Arrays.asList(embeds));
    }

    public MessageBuilder addEmbed(MessageEmbed embed) {
        embeds.add(embed);
        return this;
    }

    public MessageBuilder removeEmbed(int index) {
        embeds.remove(index);
        return this;
    }

    public String getContent() {
        return content;
    }

    public MessageBuilder setContent(String content) {
        this.content = content;
        return this;
    }

    public MessageBuilder appendContent(String content) {
        return setContent(getContent() + content);
    }

    public MessageBuilder prependContent(String content) {
        return setContent(content + getContent());
    }

    public Message build() {
        return new Message(null, tts, null, false, -1, embeds, null, content, null, null, null);
    }

    public static MessageBuilder from(Message message) {
        return new MessageBuilder()
                .setTts(message.isTts())
                .setContent(message.getContent())
                .setEmbeds(message.getEmbeds());
    }

}