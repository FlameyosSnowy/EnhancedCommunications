package me.flame.communication.events.actions;

import me.flame.communication.actions.types.ExecuteCommandAction;
import me.flame.communication.data.MessageDataRegistry;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import org.jetbrains.annotations.NotNull;

public class PreCommandExecuteEvent extends Event implements Cancellable {
    private static final HandlerList HANDLER_LIST = new HandlerList();

    private final ExecuteCommandAction action;
    private final MessageDataRegistry<String> registry;
    private boolean cancelled;

    public PreCommandExecuteEvent(final boolean async, final ExecuteCommandAction action, final MessageDataRegistry<String> registry) {
        super(async);
        this.action = action;
        this.registry = registry;
    }

    public ExecuteCommandAction getAction() {
        return action;
    }

    public MessageDataRegistry<String> getRegistry() {
        return registry;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(final boolean b) {
        this.cancelled = b;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLER_LIST;
    }
}
