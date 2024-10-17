package me.flame.communication.modifier;

import me.flame.communication.EnhancedCommunication;
import me.flame.communication.data.GroupedDataRegistry;
import me.flame.communication.data.RawDataRegistry;
import me.flame.communication.managers.MentionsManager;
import org.jetbrains.annotations.NotNull;

public class MentionInsertionMessageModifier implements MessageModifier {
    @Override
    public ModifierPriority getPriority() {
        return ModifierPriority.NORMAL;
    }

    @Override
    public String modify(final @NotNull RawDataRegistry dataRegistry) {
        MentionsManager mentionsManager = EnhancedCommunication.get().getChatManager().getMentionsManager();

        String message = dataRegistry.getMessage();
        return mentionsManager.changeMentionsLook(dataRegistry.getPlayer(), message)
                .map(GroupedDataRegistry::getMessage)
                .orElseGet(message);
    }
}
