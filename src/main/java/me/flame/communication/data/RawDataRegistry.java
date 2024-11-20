package me.flame.communication.data;

import org.bukkit.entity.Player;

@SuppressWarnings("unused")
public interface RawDataRegistry {
    /**
     * Gets the player associated with this registry.
     *
     * @return the player associated with this registry
     */
    Player getPlayer();

    /**
     * Retrieves the message associated with this registry.
     *
     * @return the message as a String
     */
    String getMessage();

    /**
     * Sets the message associated with this registry.
     *
     * @param message the message to be set
     */
    void setMessage(String message);

    /**
     * Registers a value under the given key in the registry.
     *
     * @param key   the key under which the value should be registered
     * @param value the value which should be registered
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
     * Gets the number of registrations in this registry.
     *
     * @return the number of registrations as an integer
     */
    int getRegistrationsSize();
}
