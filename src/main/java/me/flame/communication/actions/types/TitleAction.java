package me.flame.communication.actions.types;

import me.flame.communication.actions.Action;

import net.kyori.adventure.title.Title;

import org.bukkit.entity.Player;

import org.jetbrains.annotations.NotNull;

public record TitleAction(Title title) implements Action {

    @Override
    public int expectedArgs() {
        return 5;
    }

    @Override
    public void execute(@NotNull Player involvedPlayer) {
        involvedPlayer.showTitle(title);
    }
}
