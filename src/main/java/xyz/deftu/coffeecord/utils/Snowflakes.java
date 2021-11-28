package xyz.deftu.coffeecord.utils;

import xyz.deftu.coffeecord.entities.ISnowflake;

import java.time.OffsetDateTime;
import java.util.Calendar;
import java.util.TimeZone;

public class Snowflakes {

    public static final long DISCORD_EPOCH = 1420070400000L;
    public static final long TIMESTAMP_OFFSET = 22;

    public static OffsetDateTime getTimeCreated(long snowflake) {
        long timestamp = (snowflake >>> TIMESTAMP_OFFSET) + DISCORD_EPOCH;
        Calendar gmt = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        gmt.setTimeInMillis(timestamp);
        return OffsetDateTime.ofInstant(gmt.toInstant(), gmt.getTimeZone().toZoneId());
    }

    public static OffsetDateTime getTimeCreated(ISnowflake snowflake) {
        return getTimeCreated(snowflake.getId());
    }

}