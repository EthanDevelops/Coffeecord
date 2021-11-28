package xyz.deftu.blurp4j.interfaces;

import xyz.qalcyo.json.entities.JsonElement;

@FunctionalInterface
public interface IJsonifiable<T extends JsonElement> {
    /**
     * Creates and returns a JSON representation of this object.
     *
     * @return A JSON representation of this object.
     */
    T jsonify();
}