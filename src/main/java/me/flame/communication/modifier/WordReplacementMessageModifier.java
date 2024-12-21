package me.flame.communication.modifier;

import me.flame.communication.EnhancedCommunication;
import me.flame.communication.messages.SerializedMessage;

public class WordReplacementMessageModifier implements MessageModifier {
    @Override
    public ModifierPriority getPriority() {
        return ModifierPriority.HIGHEST;
    }

    @Override
    public void modify(final SerializedMessage message) {
        EnhancedCommunication.get().getChatManager().getWordReplacementManager().replaceWords(message);
    }
}
