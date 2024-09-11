package me.flame.communication.commands;

import me.flame.communication.EnhancedCommunication;
import me.flame.communication.managers.ChatManager;
import org.bukkit.entity.Player;

public class ReloadCommand {
    public void onReloadCommand(Player sender) {
        EnhancedCommunication.get().getMessagesConfig().reload();
        EnhancedCommunication.get().getPrimaryConfig().reload();
        EnhancedCommunication.get().getChatFilterConfig().reload();

        ChatManager chatManager = EnhancedCommunication.get().getChatManager();
        chatManager.reload();
    }
}
