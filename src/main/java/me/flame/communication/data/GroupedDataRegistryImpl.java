package me.flame.communication.data;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.*;

@SuppressWarnings("unused")
public class GroupedDataRegistryImpl extends MessageDataRegistryImpl implements GroupedDataRegistry, AutoCloseable {
    private final Set<UUID> players;

    GroupedDataRegistryImpl(final Player player,
                            final String serializedMessage,
                            final String[] words) {
        super(player, serializedMessage, words);
        this.players = new HashSet<>(words.length);
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

    @Override
    public void close() throws Exception {
        super.close();
        this.players.clear();
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