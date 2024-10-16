package me.flame.communication.events.chat;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class PreChatProcessEvent extends Event implements Cancellable {
    private final Player player;
    private final String message;
    private boolean cancelled;

    private static final HandlerList HANDLERS = new HandlerList();

    public PreChatProcessEvent(boolean async, Player player, String message) {
        super(async);
        this.player = player;
        this.message = message;
        this.cancelled = false;
    }

    public Player getPlayer() {
        return player;
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

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
