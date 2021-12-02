package xyz.deftu.coffeecord.entities.message;

public class MessageBuilder {

    private boolean tts;
    private String content;

    public boolean isTts() {
        return tts;
    }

    public MessageBuilder setTts(boolean tts) {
        this.tts = tts;
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
        return new Message(null, tts, null, false, -1, null, content, null, null, -1);
    }

}