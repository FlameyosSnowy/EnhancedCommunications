package me.flame.communication.commands;

import dev.velix.imperat.BukkitSource;
import dev.velix.imperat.annotations.*;

import me.flame.communication.managers.ChatManager;
import me.flame.communication.settings.MessagesSettings;
import me.flame.communication.settings.PrimarySettings;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

import org.jetbrains.annotations.NotNull;

@Command(value = "communications")
@Description("The main enhanced communications plugin command.")
@Permission("enchancedcommunications.admin.communications")
public class ReloadCommand {
    @Dependency
    private MessagesSettings messagesSettings;

    @Dependency
    private PrimarySettings settings;

    @Dependency
    private ChatManager chatManager;

    @SubCommand("reload")
    @Async
    public void onReloadCommand(@NotNull BukkitSource sender) {
        long start = System.nanoTime();
        this.messagesSettings.reload();
        this.settings.reload();
        this.chatManager.reload();
        long end = System.nanoTime() - start;

        sender.reply(Component.text("Reloaded \"config.yml\" and \"messages.yml\", and reloaded the plugin in " + end + "ns or " + end / 1_000_000 + "ms").color(NamedTextColor.GREEN));
    }
}
