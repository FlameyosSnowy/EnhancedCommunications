package me.flame.communication.listeners;

import me.flame.communication.EnhancedCommunication;
import me.flame.communication.events.conversation.ConversationEndEvent;
import me.flame.communication.utils.MessageData;
import me.flame.communication.utils.Messages;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class LoginListener implements Listener {
    private final EnhancedCommunication plugin;
    private final MiniMessage miniMessage = MiniMessage.miniMessage();

    @Contract(pure = true)
    public LoginListener(@NotNull final EnhancedCommunication plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onQuit(@NotNull PlayerQuitEvent event) {
        Player sender = event.getPlayer();
        this.plugin.getChatManager().getCooldownManager().removeCooldown(sender);
        this.plugin.getConversationManager().getLastMessage(sender.getUniqueId())
                .ifPresent((messageData) -> {
                    Player recipient = Objects.requireNonNull(Bukkit.getPlayer(messageData.recipient()));
                    Messages.sendServer(sender, MessageData.builder("server.recipient-discussion-ended")
                                    .replace("%recipient%", this.miniMessage.serialize(recipient.displayName()))
                                    .build());
                    Bukkit.getPluginManager().callEvent(new ConversationEndEvent(messageData));
                });

    }
}
