package me.flame.communication.events.actions;

import me.flame.communication.actions.types.SendMessageAction;
import me.flame.communication.data.MessageDataRegistry;

import net.kyori.adventure.text.Component;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import org.jetbrains.annotations.NotNull;

public class PreSendMessageExecuteEvent extends Event implements Cancellable {
    private static final HandlerList HANDLER_LIST = new HandlerList();

    private final SendMessageAction action;
    private final MessageDataRegistry<Component> registry;
    private boolean cancelled;

    public PreSendMessageExecuteEvent(final boolean async, final SendMessageAction action, final MessageDataRegistry<Component> registry) {
        super(async);
        this.action = action;
        this.registry = registry;
    }

    public SendMessageAction getAction() {
        return action;
    }

    public MessageDataRegistry<Component> getRegistry() {
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
