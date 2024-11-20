package me.flame.communication.events.chat;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;

import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class PreChatProcessEvent extends PlayerEvent implements Cancellable {
    private final String message;
    private boolean cancelled;

    private static final HandlerList HANDLERS = new HandlerList();

    public PreChatProcessEvent(boolean async, Player player, String message) {
        super(player, async);
        this.message = message;
        this.cancelled = false;
    }

    public String getMessage() {
        return message;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    /**
     * Get the list of handlers for this event.
     *
     * @return the handler list
     */
    @SuppressWarnings("unused")
    @Contract(pure = true)
    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
