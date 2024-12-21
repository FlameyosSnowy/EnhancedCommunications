package me.flame.communication.modifier;

import me.flame.communication.data.RawDataRegistry;
import me.flame.communication.messages.SerializedMessage;

public interface MessageModifier {
    /**
     * Get the priority of the message modifier.
     *
     * @return the priority
     */
    ModifierPriority getPriority();

    /**
     * Modify the message of the given {@link RawDataRegistry}.
     */
    void modify(SerializedMessage data);
}
