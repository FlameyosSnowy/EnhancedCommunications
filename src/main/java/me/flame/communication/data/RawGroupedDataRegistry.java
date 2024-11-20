package me.flame.communication.data;

import org.bukkit.entity.Player;

@SuppressWarnings("unused")
public interface RawGroupedDataRegistry extends Iterable<Player> {
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

    /**
     * Registers a value under the given key in the registry.
     *
     * @param key   the key under which the value should be registered
     * @param value the value to be registered
     */
    void addRegistration(String key, Object value);

    /**
     * Retrieves the value registered under the given key in the registry.
     *
     * @param key the key under which the value is registered
     * @return the registered value, or null if no value is registered under the given key
     */
    <E> E getRegistration(String key);

    /**
     * Retrieves the value registered under the given key in the registry, or a default value if no value is registered
     * under the given key.
     *
     * @param key          the key under which the value is registered
     * @param defaultValue the default value to return if no value is registered under the given key
     * @return the registered value, or the default value
     */
    <E> E getRegistrationOr(String key, E defaultValue);

    /**
     * Gets the number of registrations in the registry.
     *
     * @return the number of registrations in the registry
     */
    int getRegistrationsSize();
}
