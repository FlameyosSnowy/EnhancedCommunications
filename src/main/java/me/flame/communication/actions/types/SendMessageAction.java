package me.flame.communication.actions.types;

import me.flame.communication.EnhancedCommunication;
import me.flame.communication.actions.Action;
import me.flame.communication.data.MessageDataRegistry;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Player;

public class SendMessageAction implements Action {
    private final MessageDataRegistry dataRegistry;

    public SendMessageAction(MessageDataRegistry dataRegistry) {
        this.dataRegistry = dataRegistry;
    }

    @Override
    public int expectedArgs() {
        return 1;
    }

    @Override
    public void execute(final Player involvedPlayer) {
        involvedPlayer.sendMessage(MINI_MESSAGE.deserialize(this.dataRegistry.getMessage()));
    }
}
