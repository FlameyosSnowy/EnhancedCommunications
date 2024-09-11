package me.flame.communication.data;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class RawGroupedDataRegistryImpl implements RawGroupedDataRegistry {
    private final Map<String, Object> registry = new HashMap<>();
    private final Set<UUID> players = new HashSet<>();

    RawGroupedDataRegistryImpl() {
    }

    public void addRegistration(String key, Object value) {
        this.registry.put(key, value);
    }

    public <E> E getRegistration(String key) {
        return (E) this.registry.get(key);
    }

    public <E> E getRegistrationOr(String key, E defaultValue) {
        E value = (E) this.registry.get(key);
        return value == null ? defaultValue : value;
    }

    public int getRegistrationsSize() {
        return this.registry.size();
    }

    public void add(@NotNull Player player) {
        players.add(player.getUniqueId());
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
