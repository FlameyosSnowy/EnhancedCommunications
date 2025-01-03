package me.flame.communication.commands;

import dev.velix.imperat.BukkitSource;
import dev.velix.imperat.annotations.*;

import io.papermc.paper.plugin.configuration.PluginMeta;
import me.flame.communication.EnhancedCommunication;
import me.flame.communication.managers.ChatManager;
import me.flame.communication.settings.MessagesSettings;
import me.flame.communication.settings.PrimarySettings;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

import org.bukkit.plugin.PluginDescriptionFile;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import static me.flame.communication.actions.Action.MINI_MESSAGE;

@Command(value = { "enhancedcommunications", "communications", "ec" })
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

    @Usage
    @SuppressWarnings("all")
    public void onCommand(@NotNull BukkitSource sender) {
        PluginMeta description = Objects.requireNonNull(EnhancedCommunication.get().getPluginMeta());
        sender.reply(MINI_MESSAGE.deserialize("<dark_green><bold>About EnhancedCommunications<green>:"));
        sender.reply(MINI_MESSAGE.deserialize("<green><bold>+<dark_green>=========================<green>+<reset>"));
        sender.reply(MINI_MESSAGE.deserialize("<gold>Authors: " + description.getAuthors()));
        sender.reply(MINI_MESSAGE.deserialize("<gold>Description: " + description.getDescription()));
        sender.reply(MINI_MESSAGE.deserialize("<gold>API Version: " + description.getAPIVersion()));
        sender.reply(MINI_MESSAGE.deserialize("<gold>Website: " + description.getWebsite()));
        sender.reply(MINI_MESSAGE.deserialize("<gold>Version: " + description.getVersion()));
    }
}
