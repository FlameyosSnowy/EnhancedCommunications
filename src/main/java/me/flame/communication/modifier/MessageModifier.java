package me.flame.communication.modifier;

import me.flame.communication.data.RawDataRegistry;

public interface MessageModifier {
    /**
     * Get the priority of the message modifier.
     *
     * @return the priority
     */
    ModifierPriority getPriority();

    /**
     * Modify the message of the given {@link RawDataRegistry}.
     *
     * @param dataRegistry the data registry
     * @return the modified message
     */
    String modify(RawDataRegistry dataRegistry);
}
