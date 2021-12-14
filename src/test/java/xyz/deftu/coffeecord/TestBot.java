package xyz.deftu.coffeecord;

import xyz.deftu.coffeecord.commands.impl.slash.SlashCommand;
import xyz.deftu.coffeecord.commands.impl.slash.options.SlashCommandOption;
import xyz.deftu.coffeecord.entities.message.MessageBuilder;
import xyz.deftu.coffeecord.events.interactions.SlashCommandEvent;
import xyz.deftu.coffeecord.events.messages.MessageReceivedEvent;
import xyz.deftu.coffeecord.interactions.InteractionDeferred;
import xyz.deftu.coffeecord.socket.GatewayIntent;
import xyz.deftu.deftils.Multithreading;
import xyz.deftu.eventbus.SubscribeEvent;

import java.util.concurrent.TimeUnit;

public class TestBot {

    private final DiscordClient client = new DiscordClient();

    public void start() {
        client.addIntents(GatewayIntent.GUILD_MESSAGES);
        client.login(System.getProperty("coffeecord.test.token")/* Set the bot token in JVM arguments. */);
        client.getGlobalCommandManager().create(new SlashCommand("test", "Simple test command for Coffeecord")
                .addOption(
                        SlashCommandOption.subcommand("test1", "Simple test for Coffeecord subcommands.")
                                .addOption(
                                        SlashCommandOption.string("optiontest", "Test option.")
                                                .addChoice("Choice Test", "choicetest")
                                                .addChoice("Choice Test 2", "choicetest2")
                                )
                )
                .addOption(
                        SlashCommandOption.subcommand("test2", "Simple test 2 for Coffeecord subcommands.")
                                .addOption(
                                        SlashCommandOption.integer("optiontest2", "Test integer.")
                                                .addChoice("Singular", 1)
                                                .addChoice("Double", 2)
                                )
                )
        );
        client.getEventBus().register(this);
    }

    @SubscribeEvent
    private void onSlashCommand(SlashCommandEvent event) {
        InteractionDeferred deferred = event.defer();
        Multithreading.schedule(() -> {
            deferred.edit(new MessageBuilder()
                    .setContent("This message was deferred.")
                    .build());
            Multithreading.schedule(() -> {
                deferred.edit(new MessageBuilder()
                        .setContent("Deferred twice!")
                        .build());
            }, 5, TimeUnit.SECONDS);
        }, 5, TimeUnit.SECONDS);
    }

    public static void main(String[] args) {
        new TestBot().start();
    }

}