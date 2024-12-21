package me.flame.communication.actions.types;

import me.flame.communication.actions.Action;

import me.flame.communication.data.MessageDataRegistry;
import me.flame.communication.events.actions.PreSoundExecuteEvent;
import me.flame.communication.utils.SoundData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public record SoundAction(MessageDataRegistry<SoundData> dataRegistry) implements Action {
    @Override
    public int expectedArgs() {
        return 3;
    }

    @Override
    public void execute(@NotNull Player involvedPlayer) {
        PreSoundExecuteEvent event = new PreSoundExecuteEvent(!Bukkit.isPrimaryThread(), this, this.dataRegistry);
        Bukkit.getPluginManager().callEvent(event);

        if (event.isCancelled()) {
            return;
        }

        SoundData data = this.dataRegistry.getData();
        involvedPlayer.playSound(involvedPlayer.getLocation(), data.sound(), data.volume(), data.pitch());
    }
}
