package xyz.deftu.coffeecord

import xyz.deftu.coffeecord.commands.impl.slash.SlashCommand

fun slashCommand(builder: SlashCommandBuilder.() -> Unit): SlashCommand = SlashCommandBuilder()
    .apply(builder).build()

class SlashCommandBuilder {
    var name: String? = null
    var description: String? = null
    fun build(): SlashCommand {
        if (name.isNullOrEmpty())
            throw IllegalStateException("Slash command name cannot be null/empty.")
        if (description.isNullOrEmpty())
            throw IllegalStateException("Slash command description cannot be null/empty.")

        return SlashCommand(name, description)
    }
}