package me.flame.communication.managers.impl;

import me.flame.communication.EnhancedCommunication;
import me.flame.communication.data.RawDataRegistry;
import me.flame.communication.managers.MessageModifierManager;
import me.flame.communication.modifier.ChatFormatMessageModifier;
import me.flame.communication.modifier.MentionInsertionMessageModifier;
import me.flame.communication.modifier.MessageModifier;
import me.flame.communication.modifier.WordReplacementMessageModifier;
import me.flame.communication.utils.Reloadable;

import java.util.PriorityQueue;
import java.util.Queue;

public class MessageModifierManagerImpl implements Reloadable, MessageModifierManager {
    private final Queue<MessageModifier> modifiers;

    public MessageModifierManagerImpl() {
        this.modifiers = new PriorityQueue<>(MESSAGE_MODIFIER_COMPARATOR);
        this.initializeDefaultModifiers();
    }

    private void initializeDefaultModifiers() {
        if (EnhancedCommunication.get().getPrimaryConfig().isMentionsEnabled()) {
            this.addModifier(new MentionInsertionMessageModifier());
        }

        if (!EnhancedCommunication.get().getPrimaryConfig().getWordReplacements().isEmpty()) {
            this.addModifier(new WordReplacementMessageModifier());
        }

        if (!EnhancedCommunication.get().getPrimaryConfig().getGroupFormat().isEmpty()) {
            this.addModifier(new ChatFormatMessageModifier());
        }
    }

    public void addModifier(MessageModifier modifier) {
        this.modifiers.add(modifier);
    }

    @Override
    public void removeModifier(final MessageModifier modifier) {
        this.modifiers.remove(modifier);
    }

    public void editMessage(RawDataRegistry dataRegistry) {
        for (MessageModifier modifier : modifiers) {
            String newMessage = modifier.modify(dataRegistry);
            if (newMessage.isEmpty()) continue;

            dataRegistry.setMessage(newMessage);
        }
    }

    @Override
    public void reload() {
        this.modifiers.clear();
        initializeDefaultModifiers();
    }
}
