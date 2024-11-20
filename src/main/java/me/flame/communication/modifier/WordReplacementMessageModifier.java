package me.flame.communication.modifier;

import me.flame.communication.EnhancedCommunication;
import me.flame.communication.data.RawDataRegistry;

public class WordReplacementMessageModifier implements MessageModifier {
    @Override
    public ModifierPriority getPriority() {
        return ModifierPriority.HIGHEST;
    }

    @Override
    public String modify(final RawDataRegistry dataRegistry) {
        String message = dataRegistry.getMessage();
        return EnhancedCommunication.get().getChatManager().getWordReplacementManager().replaceWords(message);
    }
}
