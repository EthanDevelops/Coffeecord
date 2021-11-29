package xyz.deftu.coffeecord.entities;

import com.google.gson.JsonElement;

public interface IJsonifiable<T extends JsonElement> {
    T asJson();
}