package xyz.deftu.blurp4j.entities.messages.components.impl;

import xyz.deftu.blurp4j.entities.messages.components.MessageComponent;
import xyz.deftu.blurp4j.interfaces.IBuilder;
import xyz.deftu.blurp4j.interfaces.IJsonifiable;
import xyz.qalcyo.json.entities.JsonArray;
import xyz.qalcyo.json.entities.JsonObject;
import xyz.qalcyo.mango.Lists;
import xyz.qalcyo.mango.Strings;

import java.util.List;

public class SelectionMenuComponent extends MessageComponent {

    private final String id, placeholder;
    private final List<SelectOption> options;
    private final int min, max;
    private final boolean disabled;

    public SelectionMenuComponent(String id, String placeholder, List<SelectOption> options, int min, int max, boolean disabled) {
        super(3);
        this.id = id;
        this.placeholder = placeholder;
        this.options = options;
        this.min = min;
        this.max = max;
        this.disabled = disabled;
    }

    public void json(JsonObject object) {
        if (Strings.isNullOrEmpty(id))
            throw new IllegalStateException("Selection Menu must have an ID!");

        object.add("custom_id", id);
        if (!Strings.isNullOrEmpty(placeholder))
            object.add("placeholder", placeholder);

        JsonArray options = new JsonArray();
        for (SelectOption option : this.options) {
            options.add(option.jsonify());
        }
        object.add("options", options);

        if (min > -1)
            object.add("min_values", min);
        if (max > -1)
            object.add("max_values", max);
        object.add("disabled", disabled);
    }

    public static SelectionMenuComponentBuilder newBuilder() {
        return new SelectionMenuComponentBuilder();
    }

    public static SelectOptionBuilder newOption() {
        return new SelectOptionBuilder();
    }

    public static class SelectionMenuComponentBuilder implements IBuilder<SelectionMenuComponent> {

        private String id, placeholder;
        private List<SelectOption> options = Lists.newArrayList();
        private int min = -1, max = -1;
        private boolean disabled;

        public String id() {
            return id;
        }

        public SelectionMenuComponentBuilder id(String id) {
            this.id = id;
            return this;
        }

        public String placeholder() {
            return placeholder;
        }

        public SelectionMenuComponentBuilder placeholder(String placeholder) {
            this.placeholder = placeholder;
            return this;
        }

        public List<SelectOption> options() {
            return options;
        }

        public SelectionMenuComponentBuilder options(List<SelectOption> options) {
            this.options = options;
            return this;
        }

        public SelectionMenuComponentBuilder option(SelectOption option) {
            options.add(option);
            return this;
        }

        public SelectionMenuComponentBuilder option(int index) {
            options.remove(index);
            return this;
        }

        public int min() {
            return min;
        }

        public SelectionMenuComponentBuilder min(int min) {
            this.min = min;
            return this;
        }

        public int max() {
            return max;
        }

        public SelectionMenuComponentBuilder max(int max) {
            this.max = max;
            return this;
        }

        public boolean disabled() {
            return disabled;
        }

        public SelectionMenuComponentBuilder disabled(boolean disabled) {
            this.disabled = disabled;
            return this;
        }

        public SelectionMenuComponent build() {
            return new SelectionMenuComponent(id, placeholder, options, min, max, disabled);
        }

    }

    public static class SelectOption implements IJsonifiable<JsonObject> {

        private final String label, value, description;
        private final boolean defaultSelected;

        public SelectOption(String label, String value, String description, boolean defaultSelected) {
            this.label = label;
            this.value = value;
            this.description = description;
            this.defaultSelected = defaultSelected;
        }

        public JsonObject jsonify() {
            JsonObject value = new JsonObject();

            value.add("label", label);
            value.add("value", this.value);
            value.add("description", description);
            value.add("default", defaultSelected);

            return value;
        }

    }

    public static class SelectOptionBuilder implements IBuilder<SelectOption> {

        private String label, value, description;
        private boolean defaultSelected;

        public String label() {
            return label;
        }

        public SelectOptionBuilder label(String label) {
            this.label = label;
            return this;
        }

        public String value() {
            return value;
        }

        public SelectOptionBuilder value(String value) {
            this.value = value;
            return this;
        }

        public String description() {
            return description;
        }

        public SelectOptionBuilder description(String description) {
            this.description = description;
            return this;
        }

        public boolean defaultSelected() {
            return defaultSelected;
        }

        public SelectOptionBuilder defaultSelected(boolean defaultSelected) {
            this.defaultSelected = defaultSelected;
            return this;
        }

        public SelectOption build() {
            return new SelectOption(label, value, description, defaultSelected);
        }

    }

}