package me.flame.communication.actions.types;

import me.flame.communication.actions.Action;
import me.flame.communication.data.MessageDataRegistry;
import me.flame.communication.events.actions.PreCommandExecuteEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public record ExecuteCommandAction(MessageDataRegistry<String> dataRegistry) implements Action {

    @Override
    public int expectedArgs() {
        return 1;
    }

    @Override
    public void execute(final Player involvedPlayer) {
        PreCommandExecuteEvent event = new PreCommandExecuteEvent(!Bukkit.isPrimaryThread(), this, dataRegistry);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled()) {
            return;
        }

        boolean executeAsConsole = dataRegistry.getRegistration("console");
        if (executeAsConsole) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), dataRegistry.getData());
            return;
        }
        Bukkit.dispatchCommand(involvedPlayer, dataRegistry.getData());
    }
}
