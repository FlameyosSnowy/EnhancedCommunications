package me.flame.communication.events.chat;

import me.flame.communication.data.RawDataRegistry;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class PostChatProcessEvent extends Event {
    private final Player player;
    private final String message;
    private final RawDataRegistry dataRegistry;

    private static final HandlerList HANDLERS = new HandlerList();

    public PostChatProcessEvent(Player player, String message, RawDataRegistry dataRegistry) {
        this.player = player;
        this.message = message;
        this.dataRegistry = dataRegistry;
    }

    public Player getPlayer() {
        return player;
    }

    public String getMessage() {
        return message;
    }

    public RawDataRegistry getDataRegistry() {
        return dataRegistry;
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
