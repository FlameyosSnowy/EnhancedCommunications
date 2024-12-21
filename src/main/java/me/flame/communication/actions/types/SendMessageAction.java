package me.flame.communication.actions.types;

import me.flame.communication.actions.Action;
import me.flame.communication.data.MessageDataRegistry;
import me.flame.communication.events.actions.PreSendMessageExecuteEvent;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public record SendMessageAction(MessageDataRegistry<Component> dataRegistry) implements Action {
    @Override
    public int expectedArgs() {
        return 1;
    }

    @Override
    public void execute(final @NotNull Player involvedPlayer) {
        PreSendMessageExecuteEvent event = new PreSendMessageExecuteEvent(!Bukkit.isPrimaryThread(), this, this.dataRegistry);
        Bukkit.getPluginManager().callEvent(event);

        if (event.isCancelled()) {
            return;
        }

        involvedPlayer.sendMessage(dataRegistry.getData());
    }
}
