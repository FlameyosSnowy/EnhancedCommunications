package me.flame.communication.data;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class RawDataRegistryImpl extends DataRegistryImpl implements RawDataRegistry {
    private final Player player;
    private String message;

    RawDataRegistryImpl(final Player player, final String message) {
        this.player = player;
        this.message = message;
    }

    @Override
    public Player getPlayer() {
        return player;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public void setMessage(final String message) {
        this.message = message;
    }
}
