package me.flame.communication.events.actions;

import me.flame.communication.actions.types.TitleAction;
import me.flame.communication.data.MessageDataRegistry;

import net.kyori.adventure.title.Title;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import org.jetbrains.annotations.NotNull;

public class PreTitleExecuteEvent extends Event implements Cancellable {
    private static final HandlerList HANDLER_LIST = new HandlerList();

    private final TitleAction action;
    private final MessageDataRegistry<Title> registry;
    private boolean cancelled;

    public PreTitleExecuteEvent(final boolean async, final TitleAction action, final MessageDataRegistry<Title> registry) {
        super(async);
        this.action = action;
        this.registry = registry;
    }

    public TitleAction getAction() {
        return action;
    }

    public MessageDataRegistry<Title> getRegistry() {
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