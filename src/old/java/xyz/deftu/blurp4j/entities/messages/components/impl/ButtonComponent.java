package xyz.deftu.blurp4j.entities.messages.components.impl;

import xyz.deftu.blurp4j.entities.messages.components.MessageComponent;
import xyz.deftu.blurp4j.interfaces.IBuilder;
import xyz.qalcyo.json.entities.JsonObject;
import xyz.qalcyo.mango.Strings;

public class ButtonComponent extends MessageComponent {

    private final String label, url, customId;
    private final int style;

    public ButtonComponent(String label, String url, String customId, int style) {
        super(2);
        this.label = label;
        this.url = url;
        this.customId = customId;
        this.style = style;
    }

    public void json(JsonObject object) {
        if (!Strings.isNullOrEmpty(label))
            object.add("label", label);
        if (!Strings.isNullOrEmpty(url))
            object.add("url", url);
        if (!Strings.isNullOrEmpty(customId))
            object.add("custom_id", customId);
        object.add("style", style);
    }

    public String getLabel() {
        return label;
    }

    public String getUrl() {
        return url;
    }

    public String getCustomId() {
        return customId;
    }

    public int getStyle() {
        return style;
    }

    public static ButtonMessageComponentBuilder newBuilder() {
        return new ButtonMessageComponentBuilder();
    }

    public static class ButtonMessageComponentBuilder implements IBuilder<ButtonComponent> {

        private String label, url, customId;
        private int style;

        public String label() {
            return label;
        }

        public ButtonMessageComponentBuilder label(String label) {
            this.label = label;
            return this;
        }

        public String url() {
            return url;
        }

        public ButtonMessageComponentBuilder url(String url) {
            this.url = url;
            return this;
        }

        public String customId() {
            return customId;
        }

        public ButtonMessageComponentBuilder customId(String customId) {
            this.customId = customId;
            return this;
        }

        public int style() {
            return style;
        }

        public ButtonMessageComponentBuilder style(int style) {
            this.style = style;
            return this;
        }

        public ButtonMessageComponentBuilder style(ButtonMessageComponentStyle style) {
            return style(style.getStyle());
        }

        public ButtonComponent build() {
            return new ButtonComponent(label, url, customId, style);
        }

    }

    public enum ButtonMessageComponentStyle {
        PRIMARY(1),
        SECONDARY(2),
        SUCCESS(3),
        DANGER(4),
        LINK(5);

        private final int style;
        ButtonMessageComponentStyle(int style) {
            this.style = style;
        }

        public int getStyle() {
            return style;
        }
    }

}