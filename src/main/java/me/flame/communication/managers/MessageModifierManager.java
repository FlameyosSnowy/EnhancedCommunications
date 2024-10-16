package me.flame.communication.managers;

import me.flame.communication.EnhancedCommunication;
import me.flame.communication.data.RawDataRegistry;
import me.flame.communication.modifier.ChatFormatMessageModifier;
import me.flame.communication.modifier.MentionInsertionMessageModifier;
import me.flame.communication.modifier.MessageModifier;
import me.flame.communication.utils.Reloadable;

import java.util.Comparator;
import java.util.PriorityQueue;

public interface MessageModifierManager extends Reloadable {
    Comparator<MessageModifier> MESSAGE_MODIFIER_COMPARATOR = Comparator.comparing(MessageModifier::getPriority).reversed();

    void addModifier(MessageModifier modifier);

    void removeModifier(MessageModifier modifier);

    void editMessage(RawDataRegistry dataRegistry);
}
