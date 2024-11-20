package me.flame.communication.broadcasts;

import net.kyori.adventure.text.Component;

import org.jetbrains.annotations.NotNull;

public class BroadcastImpl implements Broadcast {
    private final Component lore;
    private final BroadcastViewers viewers;
    private final long interval;

    private final String uniqueId;

    BroadcastImpl(@NotNull final Component lore, @NotNull final String uniqueId, @NotNull final BroadcastViewers viewers, final long interval) {
        this.lore = lore;
        this.uniqueId = uniqueId;
        this.viewers = viewers;
        this.interval = interval;
    }

    @Override
    public String getUniqueId() {
        return this.uniqueId;
    }

    @Override
    public Component getLore() {
        return this.lore;
    }

    @Override
    public long getInterval() {
        return this.interval;
    }

    @Override
    public BroadcastViewers getViewers() {
        return this.viewers;
    }
}
