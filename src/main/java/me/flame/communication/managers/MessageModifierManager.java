package me.flame.communication.managers;

import me.flame.communication.messages.SerializedMessage;
import me.flame.communication.modifier.MessageModifier;
import me.flame.communication.utils.Reloadable;

import java.util.Comparator;

@SuppressWarnings("unused")
public interface MessageModifierManager extends Reloadable {
    Comparator<MessageModifier> MESSAGE_MODIFIER_COMPARATOR = Comparator.comparing(MessageModifier::getPriority);

    /**
     * Adds a modifier to the message modifier manager. Modifiers are applied in order of their priority, as determined by
     * {@link MessageModifier#getPriority()}.
     *
     * @param modifier The modifier to add.
     */
    void addModifier(MessageModifier modifier);

    /**
     * Removes a modifier from the message modifier manager.
     *
     * @param modifier The modifier to remove.
     */
    void removeModifier(MessageModifier modifier);

    /**
     * Applies all registered message modifiers to the given data registry.
     *
     * @param dataRegistry The data registry to modify.
     */
    void editMessage(SerializedMessage dataRegistry);
}
