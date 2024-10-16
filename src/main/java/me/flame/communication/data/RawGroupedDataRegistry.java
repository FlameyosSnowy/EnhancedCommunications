package me.flame.communication.data;

import org.bukkit.entity.Player;

public interface RawGroupedDataRegistry extends Iterable<Player> {
    boolean add(Player player);

    void remove(Player player);

    boolean contains(Player player);

    void addRegistration(String key, Object value);

    <E> E getRegistration(String key);

    <E> E getRegistrationOr(String key, E defaultValue);

    int getRegistrationsSize();
}
