package me.flame.communication.managers.impl;

import me.flame.communication.EnhancedCommunication;
import me.flame.communication.managers.ChatFormatManager;
import me.flame.communication.messages.SerializedMessage;
import me.flame.communication.providers.ChatProvider;
import me.flame.communication.utils.Reloadable;
import org.bukkit.entity.Player;

public class ChatFormatManagerImpl implements ChatFormatManager, Reloadable {
    private ChatProvider chatProvider = ChatProvider.getChosenChatProvider(EnhancedCommunication.get().getPrimaryConfig().getChatProvider());

    @Override
    public SerializedMessage getFormat(final SerializedMessage message, final Player player) {
        if (EnhancedCommunication.get().getPrimaryConfig().getGroupFormat().isEmpty()) {
            return SerializedMessage.EMPTY;
        }
        return chatProvider.getFormat(message, EnhancedCommunication.get().getPrimaryConfig().getGroupFormat(), player);
    }

    @Override
    public void reload() {
        this.chatProvider = ChatProvider.getChosenChatProvider(EnhancedCommunication.get().getPrimaryConfig().getChatProvider());
    }
}
