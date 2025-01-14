package me.flame.communication.data;

import org.bukkit.entity.Player;

@SuppressWarnings("unused")
public interface GroupedDataRegistry<D> extends MessageDataRegistry<D>, Iterable<Player> {
    /**
     * Adds the specified player to the registry.
     *
     * @param player the player to be added
     * @return true if the player was added successfully, false if the player was already present
     */
    boolean add(Player player);

    /**
     * Removes the specified player from the registry.
     *
     * @param player the player to be removed
     */
    void remove(Player player);

    /**
     * Determines if the specified player is present in the registry.
     *
     * @param player the player to check for presence in the registry
     * @return true if the player is present, false otherwise
     */
    boolean contains(Player player);
}