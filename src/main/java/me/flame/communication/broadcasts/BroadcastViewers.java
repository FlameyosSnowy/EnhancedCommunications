package me.flame.communication.broadcasts;

import org.bukkit.Bukkit;
import org.bukkit.World;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public interface BroadcastViewers {
    class Lazy {
        static final BroadcastViewers GLOBAL = new BroadcastViewersImpl(true, Bukkit.getWorlds());
    }

    /**
     * Create a new {@link BroadcastViewers} that only targets the given worlds.
     *
     * @param worlds the list of worlds to target
     * @return a new {@link BroadcastViewers} instance
     */
    @NotNull
    @Contract("_ -> new")
    static BroadcastViewers create(List<World> worlds) {
        return new BroadcastViewersImpl(false, worlds);
    }

    /**
     * Retrieve a global {@link BroadcastViewers} instance that targets all available worlds.
     *
     * @return a global {@link BroadcastViewers} instance
     */
    @NotNull
    static BroadcastViewers global() {
        return Lazy.GLOBAL;
    }

    /**
     * Create a new {@link BroadcastViewers} that only targets the given world.
     *
     * @param world the world to target
     * @return a new {@link BroadcastViewers} instance
     */
    @NotNull
    @Contract("_ -> new")
    static BroadcastViewers create(World world) {
        return new BroadcastViewersImpl(false, Collections.singletonList(world));
    }

    /**
     * Retrieve the list of worlds that this {@link BroadcastViewers} targets.
     *
     * @return the list of worlds
     */
    List<World> getWorlds();

    /**
     * Determine if this {@link BroadcastViewers} targets all available worlds globally, or a subset of worlds.
     *
     * @return {@code true} if the broadcast viewers targets all available worlds, {@code false} otherwise
     */
    boolean isGlobal();

    /*
     * Update the players in the given world using the specified function.
     *
     * @param world the world containing the players to update
     * @param function a function that updates the list of players
     */
    // void update(final World world, final PlayerUpdaterFunction function);
}