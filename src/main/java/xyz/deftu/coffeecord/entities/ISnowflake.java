package xyz.deftu.coffeecord.entities;

import xyz.deftu.coffeecord.utils.Snowflakes;

import java.time.OffsetDateTime;

public interface ISnowflake {
    long getId();
    default OffsetDateTime getTimeCreated() {
        return Snowflakes.getTimeCreated(this);
    }
}