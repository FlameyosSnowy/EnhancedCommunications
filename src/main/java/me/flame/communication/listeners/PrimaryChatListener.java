package me.flame.communication.listeners;

import io.papermc.paper.event.player.AsyncChatEvent;
import me.flame.communication.managers.ChatManager;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class PrimaryChatListener implements Listener {
    private final ChatManager chatManager;
    private final MiniMessage miniMessage;

    @Contract(pure = true)
    public PrimaryChatListener(final ChatManager chatManager) {
        this.chatManager = chatManager;
        this.miniMessage = MiniMessage.miniMessage();
    }

    // If the event is cancelled, the message won't even need to be sent anyway
    // `ignoreCancelled` is not needed.
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onChat(@NotNull AsyncChatEvent event) {
        this.chatManager.processChat(event.getPlayer(), this.miniMessage.serialize(event.message()))
                        .map(dataRegistry -> this.miniMessage.deserialize(dataRegistry.getMessage()))
                        .peek(event::message)
                        .onError((throwable) -> event.setCancelled(true));
    }
}
