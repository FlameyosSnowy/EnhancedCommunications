package me.flame.communication.managers;

import me.flame.communication.EnhancedCommunication;
import me.flame.communication.data.RawDataRegistry;
import me.flame.communication.modifier.ChatFormatMessageModifier;
import me.flame.communication.modifier.MentionInsertionMessageModifier;
import me.flame.communication.modifier.MessageModifier;
import me.flame.communication.utils.Reloadable;

import java.util.Comparator;
import java.util.PriorityQueue;

public class MessageModifierManager implements Reloadable {
    private final PriorityQueue<MessageModifier> modifiers;
    private final MentionInsertionMessageModifier mentionsModifier = new MentionInsertionMessageModifier();
    private final ChatFormatMessageModifier chatFormatMessageModifier = new ChatFormatMessageModifier();

    private static final Comparator<MessageModifier> MESSAGE_MODIFIER_COMPARATOR =
            Comparator.comparing(MessageModifier::getPriority)
                      .reversed();

    public MessageModifierManager() {
        this.modifiers = new PriorityQueue<>(MESSAGE_MODIFIER_COMPARATOR);
        this.initializeDefaultModifiers();
    }

    private void initializeDefaultModifiers() {
        if (EnhancedCommunication.get().getPrimaryConfig().isMentionsEnabled()) {
            this.addModifier(mentionsModifier);
        }

        if (EnhancedCommunication.get().getPrimaryConfig().isChatProviderEnabled()) {
            this.addModifier(chatFormatMessageModifier);
        }
    }

    public void addModifier(MessageModifier modifier) {
        this.modifiers.add(modifier);
    }

    public void editMessage(RawDataRegistry dataRegistry) {
        for (MessageModifier modifier : modifiers) {
            dataRegistry.setMessage(modifier.modify(dataRegistry));
        }
    }

    @Override
    public void reload() {
        this.modifiers.remove(mentionsModifier);
        this.modifiers.remove(chatFormatMessageModifier);
        initializeDefaultModifiers();
    }
}
