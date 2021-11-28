package xyz.deftu.blurp4j.commands;

import xyz.deftu.blurp4j.interfaces.IBuilder;
import xyz.deftu.blurp4j.interfaces.IJsonifiable;
import xyz.qalcyo.json.entities.JsonArray;
import xyz.qalcyo.json.entities.JsonObject;
import xyz.qalcyo.mango.Strings;

import java.util.ArrayList;
import java.util.List;

public class SlashCommand implements IJsonifiable<JsonObject> {

    private final String name, description;
    private final List<SlashCommandOption> options;

    public SlashCommand(String name, String description, List<SlashCommandOption> options) {
        this.name = name;
        this.description = description;
        this.options = options;
    }

    public JsonObject jsonify() {
        JsonObject value = new JsonObject();

        value.add("name", name);
        String description = this.description;
        if (Strings.isNullOrEmpty(description))
            description = "";
        value.add("description", description);

        if (options != null && !options.isEmpty()) {
            JsonArray options = new JsonArray();
            for (SlashCommandOption option : this.options)
                options.add(option.jsonify());
            value.add("options", options);
        }

        return value;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<SlashCommandOption> getOptions() {
        return options;
    }

    public static SlashCommandBuilder newBuilder() {
        return new SlashCommandBuilder();
    }

    public static SlashCommandOptionBuilder newOption() {
        return new SlashCommandOptionBuilder();
    }

    public static <T> SlashCommandOptionChoiceBuilder<T> newOptionChoice() {
        return new SlashCommandOptionChoiceBuilder<>();
    }

    public static class SlashCommandOption implements IJsonifiable<JsonObject> {

        private final String name, description;
        private final int type;
        private final boolean required;
        private final List<SlashCommandOptionChoice<?>> choices;

        private Object value = null;

        public SlashCommandOption(String name, String description, int type, boolean required, List<SlashCommandOptionChoice<?>> choices) {
            this.name = name;
            this.description = description;
            this.type = type;
            this.required = required;
            this.choices = choices;
        }

        public String getName() {
            return name;
        }

        public String getDescription() {
            return description;
        }

        public int getType() {
            return type;
        }

        public boolean isRequired() {
            return required;
        }

        public Object getValue() {
            if (value == null)
                throw new UnsupportedOperationException("This slash command option is not a response option, it doesn't have a value!");
            return value;
        }

        public SlashCommandOption setValue(Object value) {
            this.value = value;
            return this;
        }

        public JsonObject jsonify() {
            JsonObject value = new JsonObject();

            value.add("name", name);
            if (!Strings.isNullOrEmpty(description))
                value.add("description", description);
            value.add("type", type);
            value.add("required", required);

            if (choices != null && !choices.isEmpty()) {
                JsonArray choices = new JsonArray();
                for (SlashCommandOptionChoice<?> choice : this.choices) {
                    choices.add(choice.jsonify());
                }
                value.add("choices", choices);
            }

            return value;
        }

        public static SlashCommandOption fromJson(JsonObject object) {
            return new SlashCommandOption(object.getAsString("name"), null, object.getAsInt("type"), false, new ArrayList<>()).setValue(object.getAsPrimitive("value").getValue());
        }

    }

    public static class SlashCommandOptionBuilder implements IBuilder<SlashCommandOption> {

        private String name, description;
        private int type;
        private boolean required;
        private List<SlashCommandOptionChoice<?>> choices = new ArrayList<>();

        public String name() {
            return name;
        }

        public SlashCommandOptionBuilder name(String name) {
            this.name = name;
            return this;
        }

        public String description() {
            return description;
        }

        public SlashCommandOptionBuilder description(String description) {
            this.description = description;
            return this;
        }

        public int type() {
            return type;
        }

        public SlashCommandOptionBuilder type(int type) {
            this.type = type;
            return this;
        }

        public SlashCommandOptionBuilder type(SlashCommandType type) {
            return type(type.getValue());
        }

        public boolean required() {
            return required;
        }

        public SlashCommandOptionBuilder required(boolean required) {
            this.required = required;
            return this;
        }

        public List<SlashCommandOptionChoice<?>> choices() {
            return choices;
        }

        public SlashCommandOptionBuilder choices(List<SlashCommandOptionChoice<?>> choices) {
            this.choices = choices;
            return this;
        }

        public SlashCommandOptionBuilder choice(SlashCommandOptionChoice<?> choice) {
            choices.add(choice);
            return this;
        }

        public SlashCommandOptionBuilder choice(int index) {
            choices.remove(index);
            return this;
        }

        public SlashCommandOption build() {
            return new SlashCommandOption(name, description, type, required, choices);
        }

    }

    public static class SlashCommandOptionChoice<T> implements IJsonifiable<JsonObject> {

        private final String name;
        private final T value;

        public SlashCommandOptionChoice(String name, T value) {
            this.name = name;
            this.value = value;
        }

        public JsonObject jsonify() {
            JsonObject value = new JsonObject();

            value.add("name", name);
            value.add("value", this.value);

            return value;
        }

    }

    public static class SlashCommandOptionChoiceBuilder<T> implements IBuilder<SlashCommandOptionChoice<T>> {

        private String name;
        private T value;

        public String name() {
            return name;
        }

        public SlashCommandOptionChoiceBuilder<T> name(String name) {
            this.name = name;
            return this;
        }

        public T value() {
            return value;
        }

        public SlashCommandOptionChoiceBuilder<T> value(T value) {
            this.value = value;
            return this;
        }

        public SlashCommandOptionChoice<T> build() {
            return new SlashCommandOptionChoice<>(name, value);
        }

    }

    public static class SlashCommandBuilder implements IBuilder<SlashCommand> {

        private String name, description;
        private List<SlashCommandOption> options = new ArrayList<>();

        public String name() {
            return name;
        }

        public SlashCommandBuilder name(String name) {
            this.name = name;
            return this;
        }

        public String description() {
            return description;
        }

        public SlashCommandBuilder description(String description) {
            this.description = description;
            return this;
        }

        public List<SlashCommandOption> options() {
            return options;
        }

        public SlashCommandBuilder options(List<SlashCommandOption> options) {
            this.options = options;
            return this;
        }

        public SlashCommandBuilder option(SlashCommandOption option) {
            options.add(option);
            return this;
        }

        public SlashCommandBuilder option(int index) {
            options.remove(index);
            return this;
        }

        public SlashCommand build() {
            return new SlashCommand(name, description, options);
        }

    }

}