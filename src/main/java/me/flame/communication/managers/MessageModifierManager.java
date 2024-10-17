package me.flame.communication.managers;

import me.flame.communication.data.RawDataRegistry;
import me.flame.communication.modifier.MessageModifier;
import me.flame.communication.utils.Reloadable;

import java.util.Comparator;

public interface MessageModifierManager extends Reloadable {
    Comparator<MessageModifier> MESSAGE_MODIFIER_COMPARATOR = Comparator.comparing(MessageModifier::getPriority);

    void addModifier(MessageModifier modifier);

    void removeModifier(MessageModifier modifier);

    void editMessage(RawDataRegistry dataRegistry);
}
