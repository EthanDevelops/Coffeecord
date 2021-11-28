package xyz.deftu.blurp4j.commands;

public enum SlashCommandType {

    SUBCOMMAND(1),
    SUBCOMMAND_GROUP(2),
    STRING(3),
    INTEGER(4),
    BOOLEAN(5),
    USER(6),
    CHANNEL(7),
    ROLE(8),
    MENTIONABLE(9),
    NUMBER(10);

    public final int value;
    SlashCommandType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}