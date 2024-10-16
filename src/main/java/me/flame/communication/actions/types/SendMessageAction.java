package me.flame.communication.actions.types;

import me.flame.communication.EnhancedCommunication;
import me.flame.communication.actions.Action;
import me.flame.communication.data.MessageDataRegistry;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public record SendMessageAction(Component message) implements Action {

    public SendMessageAction(String message) {
        this(MINI_MESSAGE.deserialize(message));
    }

    @Override
    public int expectedArgs() {
        return 1;
    }

    @Override
    public void execute(final @NotNull Player involvedPlayer) {
        involvedPlayer.sendMessage(message);
    }
}
