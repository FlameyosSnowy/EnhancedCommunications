package me.flame.communication.data;

import me.flame.communication.data.impl.DataRegistryImpl;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
public interface DataRegistry {
    @NotNull
    @Contract(value = " -> new", pure = true)
    static DataRegistry create() {
        return new DataRegistryImpl();
    }

    /**
     * Registers a value under the given key in the registry.
     *
     * @param key   the key under which the value should be registered
     * @param value the value which should be registered
     */
    void addRegistration(String key, Object value);

    /**
     * Gets the value registered under the given key from the registry.
     *
     * @param key the key under which the value is registered
     * @return the registered value, or null if no value is registered under the given key
     */
    <E> E getRegistration(String key);

    /**
     * Gets the value registered under the given key from the registry, or a default
     * value if no value is registered under the given key.
     *
     * @param key          the key under which the value is registered
     * @param defaultValue the default value to return if no value is registered
     *                     under the given key
     * @return the registered value, or the default value
     */
    <E> E getRegistrationOr(String key, E defaultValue);

    /**
     * Gets the number of registrations in this registry.
     *
     * @return the number of registrations in this registry
     */
    int getRegistrationsSize();
}
