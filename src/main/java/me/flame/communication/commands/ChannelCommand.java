package me.flame.communication.commands;

import me.flame.communication.channels.Channel;
import me.flame.communication.managers.ConversationManager;
import me.flame.communication.managers.PlayerChannelManager;
import me.flame.communication.messages.Message;
import org.bukkit.entity.Player;

public class ChannelCommand {
    private final PlayerChannelManager playerChannelManager;

    public ChannelCommand(final PlayerChannelManager conversationManager) {
        this.playerChannelManager = conversationManager;
    }

    public void onChannelCommand(Player sender,
                                 Channel channel) {

    }
}
