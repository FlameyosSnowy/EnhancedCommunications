package me.flame.communication.actions.types;

import me.flame.communication.actions.Action;

import me.flame.communication.data.MessageDataRegistry;
import me.flame.communication.events.actions.PreTitleExecuteEvent;
import net.kyori.adventure.title.Title;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import org.jetbrains.annotations.NotNull;

public record TitleAction(MessageDataRegistry<Title> registry) implements Action {

    @Override
    public int expectedArgs() {
        return 5;
    }

    @Override
    public void execute(@NotNull Player involvedPlayer) {
        PreTitleExecuteEvent event = new PreTitleExecuteEvent(!Bukkit.isPrimaryThread(), this, this.registry);
        Bukkit.getPluginManager().callEvent(event);

        if (event.isCancelled()) {
            return;
        }

        involvedPlayer.showTitle(registry.getData());
    }
}
