package me.flame.communication.actions.types;

import me.flame.communication.actions.Action;
import me.flame.communication.data.DataRegistry;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public record SoundAction(Sound sound, float volume, float pitch) implements Action {
    @Override
    public int expectedArgs() {
        return 3;
    }

    @Override
    public void execute(@NotNull Player involvedPlayer) {
        involvedPlayer.playSound(involvedPlayer.getLocation(), this.sound, this.volume, this.pitch);
    }
}
