package me.flame.communication.events.actions;

import me.flame.communication.actions.types.ActionBarAction;
import me.flame.communication.data.MessageDataRegistry;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class PreActionBarExecuteEvent extends Event implements Cancellable {
    private static final HandlerList HANDLER_LIST = new HandlerList();

    private final ActionBarAction action;
    private final MessageDataRegistry<String> registry;
    private boolean cancelled;

    public PreActionBarExecuteEvent(final boolean async, final ActionBarAction action, final MessageDataRegistry<String> registry) {
        super(async);
        this.action = action;
        this.registry = registry;
    }

    public ActionBarAction getAction() {
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
