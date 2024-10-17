package me.flame.communication.listeners;

import io.papermc.paper.event.player.AsyncChatEvent;

import me.flame.communication.EnhancedCommunication;
import me.flame.communication.data.RawDataRegistry;
import me.flame.communication.events.chat.PostChatProcessEvent;
import me.flame.communication.events.chat.PreChatProcessEvent;
import me.flame.communication.managers.ChatManager;

import me.flame.communication.utils.ServerHelper;
import net.kyori.adventure.text.minimessage.MiniMessage;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class PrimaryChatListener implements Listener {
    private final ChatManager chatManager;
    private final MiniMessage miniMessage;

    @Contract(pure = true)
    public PrimaryChatListener(final EnhancedCommunication plugin) {
        this.chatManager = plugin.getChatManager();
        this.miniMessage = MiniMessage.miniMessage();
    }

    // If the event is cancelled, the message won't even need to be sent anyway
    // `ignoreCancelled` is not needed.
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onChat(@NotNull AsyncChatEvent event) {
        Player player = event.getPlayer();
        String message = this.miniMessage.serialize(event.message());

        PreChatProcessEvent preEvent = new PreChatProcessEvent(event.isAsynchronous(), player, message);
        Bukkit.getServer().getPluginManager().callEvent(preEvent);

        if (preEvent.isCancelled()) {
            event.setCancelled(true);
            return;
        }

        this.chatManager.processChat(player, message)
                .peek((dataRegistry) -> {
                    event.renderer(this.chatManager.getDefaultChatRenderer().createChatRenderer(dataRegistry));
                    ServerHelper.runDelayed(new PostChatProcess(player, message, dataRegistry), 1);
                })
                .onEmpty(() -> event.setCancelled(true));
    }

    private record PostChatProcess(Player player, String serializedMessage, RawDataRegistry dataRegistry) implements Runnable {
        @Override
        public void run() {
            PostChatProcessEvent event = new PostChatProcessEvent(this.player, this.serializedMessage, this.dataRegistry);
            Bukkit.getPluginManager().callEvent(event);
        }
    }
}
