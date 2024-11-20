package me.flame.communication.utils;

public interface Reloadable {
    /**
     * Reloads the current configuration or settings, clearing any cached values
     * and reinitializing components as necessary. Implementations should define
     * the specific behavior for reloading resources.
     */
    void reload();
}
