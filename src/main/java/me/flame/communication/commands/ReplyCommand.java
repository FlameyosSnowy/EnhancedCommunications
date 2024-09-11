package me.flame.communication.commands;

import me.flame.communication.EnhancedCommunication;
import me.flame.communication.managers.ConversationManager;
import me.flame.communication.managers.ReplySuccess;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ReplyCommand {
    private final ConversationManager conversationManager;
    private final EnhancedCommunication plugin = EnhancedCommunication.get();

    public ReplyCommand(final ConversationManager conversationManager) {
        this.conversationManager = conversationManager;
    }

    public void onReplyCommand(@NotNull Player sender,
                               String message) {
        ReplySuccess success = this.conversationManager.replyToLastMessage(sender.getUniqueId(), message);
        if (success != ReplySuccess.NOT_MESSAGED) return;

        this.plugin.getMessagesConfig().send(sender, "server.no-messages-to-reply-to");
    }
}
