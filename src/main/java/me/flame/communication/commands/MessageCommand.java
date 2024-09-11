package me.flame.communication.commands;

import me.flame.communication.events.ConversationStartEvent;
import me.flame.communication.managers.ConversationManager;
import me.flame.communication.messages.Message;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class MessageCommand {
    private final ConversationManager conversationManager;

    public MessageCommand(final ConversationManager conversationManager) {
        this.conversationManager = conversationManager;
    }

    public void onMessageCommand(Player sender,
                                 Player target,
                                 String message) {
        Message messageData = new Message(sender.getUniqueId(), target.getUniqueId(), message);
        conversationManager.sendMessage(messageData);
    }
}