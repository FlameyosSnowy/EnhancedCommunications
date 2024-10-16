package me.flame.communication.utils;

import me.flame.communication.EnhancedCommunication;
import me.flame.communication.settings.MessagesSettings;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Messages {
    public static void sendServer(Player player, @NotNull MessageData data) {
        sendServer(player, data.content());
    }

    public static void sendServer(Player player, String message) {
        MessagesSettings messagesSettings = EnhancedCommunication.get().getMessagesConfig();
        String prefix = messagesSettings.get("server.prefix");
        if (!prefix.isEmpty()) {
            prefix += " ";
        }

        player.sendMessage(MiniMessage.miniMessage().deserialize(prefix)
                .append(MiniMessage.miniMessage().deserialize(message)));
    }

    public static void sendDeveloper(Player player, @NotNull MessageData data) {
        sendDeveloper(player, data.content());
    }

    public static void sendDeveloper(Player player, String message) {
        MessagesSettings messagesSettings = EnhancedCommunication.get().getMessagesConfig();
        String prefix = messagesSettings.get("developer.prefix");
        if (!prefix.isEmpty()) {
            prefix += " ";
        }

        player.sendMessage(MiniMessage.miniMessage().deserialize(prefix)
                .append(MiniMessage.miniMessage().deserialize(message)));
    }
}
