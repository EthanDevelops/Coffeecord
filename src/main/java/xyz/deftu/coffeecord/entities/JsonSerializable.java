package xyz.deftu.coffeecord.entities;

import com.google.gson.JsonElement;

public interface JsonSerializable<T extends JsonElement> {
    T asJson();
}