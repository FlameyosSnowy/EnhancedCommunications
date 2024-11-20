package me.flame.communication.broadcasts;

import org.bukkit.World;

import java.util.List;

public class BroadcastViewersImpl implements BroadcastViewers {
    private final boolean global;

    private final List<World> worlds;

    BroadcastViewersImpl(final boolean global, final List<World> worlds) {
        this.global = global;
        this.worlds = worlds;
    }

    @Override
    public List<World> getWorlds() {
        return worlds;
    }

    @Override
    public boolean isGlobal() {
        return global;
    }
}
