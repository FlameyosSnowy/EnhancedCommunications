package me.flame.communication.data.impl;

import me.flame.communication.data.RawDataRegistry;
import org.bukkit.entity.Player;

public class RawDataRegistryImpl<D> extends DataRegistryImpl implements RawDataRegistry<D> {
    private final Player player;
    private D message;

    public RawDataRegistryImpl(final Player player, final D message) {
        this.player = player;
        this.message = message;
    }

    @Override
    public Player getPlayer() {
        return player;
    }

    @Override
    public D getData() {
        return message;
    }

    @Override
    public void setData(final D message) {
        this.message = message;
    }
}
