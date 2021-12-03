package xyz.deftu.coffeecord.entities.message.embed;

public enum MessageEmbedType {

    RICH("rich"),
    IMAGE("image"),
    VIDEO("video"),
    GIF("gifv"),
    ARTICLE("article"),
    LINK("link");

    private final String value;
    MessageEmbedType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static MessageEmbedType from(String type) {
        for (MessageEmbedType value : values()) {
            if (value.getValue().equals(type)) {
                return value;
            }
        }

        return null;
    }

}