package xyz.deftu.blurp4j.entities.messages;

import xyz.deftu.blurp4j.interfaces.IBuilder;
import xyz.deftu.blurp4j.interfaces.IJsonifiable;
import xyz.deftu.blurp4j.entities.messages.components.MessageComponent;
import xyz.qalcyo.json.entities.JsonArray;
import xyz.qalcyo.json.entities.JsonObject;
import xyz.qalcyo.mango.Strings;

import java.util.ArrayList;
import java.util.List;

public class Message implements IJsonifiable<JsonObject> {

    private final String content;
    private final boolean tts;
    private long messageId;
    private long guildId;
    private final List<MessageEmbed> embeds;
    private final List<MessageComponent> components;

    private MessageReference referencedMessage;

    public Message(String content, boolean tts, long messageId, long guildId, List<MessageEmbed> embeds, List<MessageComponent> components, MessageReference referencedMessage) {
        this.content = content;
        this.tts = tts;
        this.messageId = messageId;
        this.guildId = guildId;
        this.embeds = embeds;
        this.components = components;
        this.referencedMessage = referencedMessage;
    }

    public JsonObject jsonify() {
        JsonObject value = new JsonObject();

        if (!Strings.isNullOrEmpty(content))
            value.add("content", content);
        value.add("tts", tts);
        if (messageId > -1)
            value.add("id", messageId);
        if (guildId > -1)
            value.add("guild_id", guildId);

        if (embeds != null && !embeds.isEmpty()) {
            JsonArray embeds = new JsonArray();
            for (MessageEmbed embed : this.embeds) {
                embeds.add(embed.jsonify());
            }
            value.add("embeds", embeds);
        }

        if (components != null && !components.isEmpty()) {
            JsonArray components = new JsonArray();
            for (MessageComponent component : this.components) {
                components.add(component.jsonify());
            }
            value.add("components", components);
        }

        if (referencedMessage != null)
            value.add("message_reference", referencedMessage.jsonify());

        return value;
    }

    public String getContent() {
        return content;
    }

    public boolean isTts() {
        return tts;
    }

    public long getMessageId() {
        return messageId;
    }

    public Message setMessageId(long messageId) {
        this.messageId = messageId;
        return this;
    }

    public long getGuildId() {
        return guildId;
    }

    public Message setGuildId(long guildId) {
        this.guildId = guildId;
        return this;
    }

    public List<MessageEmbed> getEmbeds() {
        return embeds;
    }

    public List<MessageComponent> getComponents() {
        return components;
    }

    public MessageReference getReferencedMessage() {
        return referencedMessage;
    }

    public Message setReferencedMessage(MessageReference referencedMessage) {
        this.referencedMessage = referencedMessage;
        return this;
    }

    public static MessageBuilder newBuilder() {
        return new MessageBuilder();
    }

    public static class MessageBuilder implements IBuilder<Message> {

        private String content;
        private boolean tts;
        private List<MessageEmbed> embeds = new ArrayList<>();
        private List<MessageComponent> components = new ArrayList<>();
        private MessageReference referencedMessage;

        public String content() {
            return content;
        }

        public MessageBuilder content(String content) {
            this.content = content;
            return this;
        }

        public MessageBuilder append(String content) {
            return content((this.content == null ? "" : this.content) + content);
        }

        public MessageBuilder prepend(String content) {
            return content(content + (this.content == null ? "" : this.content));
        }

        public boolean tts() {
            return tts;
        }

        public MessageBuilder tts(boolean tts) {
            this.tts = tts;
            return this;
        }

        public List<MessageEmbed> embeds() {
            return embeds;
        }

        public MessageBuilder embeds(List<MessageEmbed> embeds) {
            this.embeds = embeds;
            return this;
        }

        public MessageBuilder embed(MessageEmbed embed) {
            embeds.add(embed);
            return this;
        }

        public MessageBuilder embed(int index) {
            embeds.remove(index);
            return this;
        }

        public List<MessageComponent> components() {
            return components;
        }

        public MessageBuilder components(List<MessageComponent> components) {
            this.components = components;
            return this;
        }

        public MessageBuilder component(MessageComponent component) {
            components.add(component);
            return this;
        }

        public MessageBuilder component(int index) {
            components.remove(index);
            return this;
        }

        public MessageReference referencedMessage() {
            return referencedMessage;
        }

        public MessageBuilder referencedMessage(MessageReference referencedMessage) {
            this.referencedMessage = referencedMessage;
            return this;
        }

        public Message build() {
            return new Message(content, tts, -1, -1, embeds, components, null);
        }

    }

}