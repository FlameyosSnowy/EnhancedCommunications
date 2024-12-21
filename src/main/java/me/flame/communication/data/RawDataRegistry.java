package me.flame.communication.data;

import org.bukkit.entity.Player;

@SuppressWarnings("unused")
public interface RawDataRegistry<D> extends DataRegistry {
    /**
     * Gets the player associated with this registry.
     *
     * @return the player associated with this registry
     */
    Player getPlayer();

    /**
     * Retrieves the message associated with this registry.
     *
     * @return the message
     */
    D getData();

    /**
     * Sets the message associated with this registry.
     *
     * @param message the message to be set
     */
    void setData(D message);
}
