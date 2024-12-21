package me.flame.communication.data.impl;

import me.flame.communication.data.GroupedDataRegistry;

import org.bukkit.entity.Player;

import org.jetbrains.annotations.NotNull;

import java.util.*;

@SuppressWarnings("unused")
public class GroupedDataRegistryImpl<D> extends MessageDataRegistryImpl<D> implements GroupedDataRegistry<D>, AutoCloseable {
    private final Set<Player> players;

    public GroupedDataRegistryImpl(final Player player,
                                   final D serializedMessage,
                                   final String[] words, final Set<Player> players) {
        super(player, serializedMessage, words);
        this.players = players == null ? new HashSet<>(words == null ? 3 : words.length) : new HashSet<>(players);
    }

    public boolean add(@NotNull Player player) {
        return players.add(player);
    }

    public void remove(@NotNull Player player) {
        players.remove(player);
    }

    @Override
    public boolean contains(@NotNull final Player player) {
        return players.contains(player);
    }

    @NotNull
    @Override
    public Iterator<Player> iterator() {
        return players.iterator();
    }

    @Override
    public void close() throws Exception {
        super.close();
        this.players.clear();
    }
}
