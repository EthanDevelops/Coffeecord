package xyz.deftu.coffeecord.entities.message;

import xyz.deftu.coffeecord.entities.ISnowflake;

public interface Message extends ISnowflake {

    MessageReference getMessageReference();
    Message getReferencedMessage();

    boolean isTts();

}