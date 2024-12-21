package me.flame.communication.modifier;

import me.flame.communication.EnhancedCommunication;
import me.flame.communication.managers.MentionsManager;
import me.flame.communication.messages.SerializedMessage;

import org.jetbrains.annotations.NotNull;

public class MentionInsertionMessageModifier implements MessageModifier {
    @Override
    public ModifierPriority getPriority() {
        return ModifierPriority.NORMAL;
    }

    @Override
    public void modify(final @NotNull SerializedMessage data) {
        MentionsManager mentionsManager = EnhancedCommunication.get().getChatManager().getMentionsManager();

        mentionsManager.changeMentionsLook(data.getSender(), data);
    }
}
