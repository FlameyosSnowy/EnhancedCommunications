package me.flame.communication.actions.types;

import me.flame.communication.actions.Action;
import me.flame.communication.data.GroupedDataRegistry;
import me.flame.communication.data.MessageDataRegistry;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public record ExecuteCommandAction(MessageDataRegistry dataRegistry) implements Action {

    @Override
    public int expectedArgs() {
        return 1;
    }

    @Override
    public void execute(final Player involvedPlayer) {
        boolean executeAsConsole = dataRegistry.getRegistration("console");
        if (executeAsConsole) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), dataRegistry.getMessage());
            return;
        }
        Bukkit.dispatchCommand(involvedPlayer, dataRegistry.getMessage());
    }
}
