package me.flame.communication.data.impl;

import me.flame.communication.data.RawGroupedDataRegistry;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class RawGroupedDataRegistryImpl extends DataRegistryImpl implements RawGroupedDataRegistry {
    private final Set<UUID> players = new HashSet<>();

    RawGroupedDataRegistryImpl() {
    }

    public boolean add(@NotNull Player player) {
        return players.add(player.getUniqueId());
    }

    public void remove(@NotNull Player player) {
        players.remove(player.getUniqueId());
    }

    @Override
    public boolean contains(@NotNull final Player player) {
        return players.contains(player.getUniqueId());
    }

    @NotNull
    @Override
    public Iterator<Player> iterator() {
        return new PlayerIterator(this.players);
    }

    private static class PlayerIterator implements Iterator<Player> {
        private final Iterator<UUID> iterator;

        private PlayerIterator(@NotNull final Collection<UUID> collection) {
            this.iterator = collection.iterator();
        }

        @Override
        public boolean hasNext() {
            return iterator.hasNext();
        }

        @Override
        public Player next() {
            return Bukkit.getPlayer(iterator.next());
        }
    }
}
