# Creating
Creating slash commands is simple, you can access the main slash command class by using your client's slash commands getter `<client>.getSlashCommands()` and from there you can access everything regarding registering, deleting and editing slash commands.

To create a command, you need to use one of the `SlashCommands#register` methods. It should be self-explanatory that the one without a guild ID requirement is global and the one including a guild ID requirement is guild-specific.

# Usage
Slash command handling can be done using the `SlashCommandEvent`. It's best practice to check for your command name, and handle it like that.