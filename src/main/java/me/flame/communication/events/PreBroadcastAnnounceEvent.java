package me.flame.communication.events;

import me.flame.communication.broadcasts.Broadcast;
import me.flame.communication.broadcasts.BroadcastViewers;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
public class PreBroadcastAnnounceEvent extends Event implements Cancellable {
    private static final HandlerList HANDLER_LIST = new HandlerList();

    private boolean cancelled = false;

    private final BroadcastViewers viewers;
    private final Broadcast broadcast;

    public PreBroadcastAnnounceEvent(boolean async, BroadcastViewers viewers, final Broadcast broadcast) {
        super(async);
        this.viewers = viewers;
        this.broadcast = broadcast;
    }

    /**
     * Get the viewers of this broadcast.
     * @return The viewers of this broadcast.
     */
    public BroadcastViewers getViewers() {
        return viewers;
    }

    /**
     * Get the broadcast that will be announced.
     * @return The broadcast that will be announced.
     */
    public Broadcast getBroadcast() {
        return broadcast;
    }

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(final boolean cancel) {
        this.cancelled = cancel;
    }
}
