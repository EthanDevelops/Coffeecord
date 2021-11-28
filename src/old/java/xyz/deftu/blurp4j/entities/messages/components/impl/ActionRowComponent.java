package xyz.deftu.blurp4j.entities.messages.components.impl;

import xyz.deftu.blurp4j.entities.messages.components.MessageComponent;
import xyz.deftu.blurp4j.interfaces.IBuilder;
import xyz.qalcyo.json.entities.JsonArray;
import xyz.qalcyo.json.entities.JsonObject;

import java.util.ArrayList;
import java.util.List;

public class ActionRowComponent extends MessageComponent {

    private final List<MessageComponent> subComponents;

    public ActionRowComponent(List<MessageComponent> subComponents) {
        super(1);
        this.subComponents = subComponents;
    }

    public void json(JsonObject object) {
        JsonArray components = new JsonArray();
        for (MessageComponent subComponent : subComponents) {
            components.add(subComponent.jsonify());
        }
        object.add("components", components);
    }

    public static ActionRowMessageComponentBuilder newBuilder() {
        return new ActionRowMessageComponentBuilder();
    }

    public static class ActionRowMessageComponentBuilder implements IBuilder<ActionRowComponent> {

        private List<MessageComponent> subComponents = new ArrayList<>();

        public List<MessageComponent> subComponents() {
            return subComponents;
        }

        public ActionRowMessageComponentBuilder subComponents(List<MessageComponent> subComponents) {
            this.subComponents = subComponents;
            return this;
        }

        public ActionRowMessageComponentBuilder subComponent(MessageComponent component) {
            subComponents.add(component);
            return this;
        }

        public ActionRowMessageComponentBuilder subComponent(int index) {
            subComponents.remove(index);
            return this;
        }

        public ActionRowComponent build() {
            return new ActionRowComponent(subComponents);
        }

    }

}