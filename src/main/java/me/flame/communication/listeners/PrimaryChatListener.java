package me.flame.communication.listeners;

import io.papermc.paper.event.player.AsyncChatEvent;

import me.flame.communication.EnhancedCommunication;
import me.flame.communication.events.chat.PostChatProcessEvent;
import me.flame.communication.events.chat.PreChatProcessEvent;
import me.flame.communication.managers.ChatManager;

import me.flame.communication.messages.SerializedMessage;
import me.flame.communication.utils.ServerHelper;
import net.kyori.adventure.text.minimessage.MiniMessage;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.time.Instant;

public class PrimaryChatListener implements Listener {
    private final ChatManager chatManager;
    private final MiniMessage miniMessage;
    private final EnhancedCommunication plugin;

    @Contract(pure = true)
    public PrimaryChatListener(final EnhancedCommunication plugin) {
        this.chatManager = plugin.getChatManager();
        this.miniMessage = MiniMessage.miniMessage();
        this.plugin = plugin;
    }

    // If the event is cancelled, the message won't even need to be sent anyway
    // `ignoreCancelled` is not needed.
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onChat(@NotNull AsyncChatEvent event) {
        Player player = event.getPlayer();

        if (this.playerIsOnCooldown(player)) {
            event.setCancelled(true);
            return;
        }

        String message = this.miniMessage.serialize(event.message());

        PreChatProcessEvent preEvent = new PreChatProcessEvent(event.isAsynchronous(), player, message);
        Bukkit.getServer().getPluginManager().callEvent(preEvent);

        if (preEvent.isCancelled()) {
            event.setCancelled(true);
            return;
        }

        SerializedMessage data = new SerializedMessage(player, message, null, null);

        this.chatManager.getMessageModifierManager().editMessage(data);
        this.insertCooldownIfAllowed(player);

        event.renderer(this.chatManager.getDefaultChatRenderer().createChatRenderer(data));
        ServerHelper.runDelayedAsync(new PostChatProcess(player, message, data), 1);
    }

    private boolean playerIsOnCooldown(final Player player) {
        if (this.chatManager.getCooldownManager().hasCooldown(player)) {
            this.chatManager.getActionsManager().executeChatCooldownActions(player);
            return true;
        }
        return false;
    }

    private void insertCooldownIfAllowed(final Player player) {
        if (!this.chatManager.getCooldownManager().isChatCooldownsEnabled()) return;
        this.chatManager.getCooldownManager().insertCooldown(player, Instant.now().plusSeconds(this.plugin.getPrimaryConfig().getChatCooldown()));
    }

    private record PostChatProcess(Player player, String serializedMessage, SerializedMessage data) implements Runnable {
        @Override
        public void run() {
            PostChatProcessEvent event = new PostChatProcessEvent(this.player, this.serializedMessage, this.data);
            Bukkit.getPluginManager().callEvent(event);
        }
    }
}
