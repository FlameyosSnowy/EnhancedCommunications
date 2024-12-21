package me.flame.communication.events.actions;

import me.flame.communication.actions.types.SoundAction;
import me.flame.communication.data.MessageDataRegistry;
import me.flame.communication.utils.SoundData;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class PreSoundExecuteEvent extends Event implements Cancellable {
    private static final HandlerList HANDLER_LIST = new HandlerList();

    private final SoundAction action;
    private final MessageDataRegistry<SoundData> registry;

    private boolean cancelled = false;

    public PreSoundExecuteEvent(final boolean async, final SoundAction action, final MessageDataRegistry<SoundData> registry) {
        super(async);
        this.action = action;
        this.registry = registry;
    }
    public SoundAction getAction() {
        return action;
    }

    public MessageDataRegistry<SoundData> getRegistry() {
        return registry;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(final boolean b) {
        this.cancelled = b;
    }
}
