package me.flame.communication.actions.types;

import me.flame.communication.EnhancedCommunication;
import me.flame.communication.actions.Action;
import me.flame.communication.data.DataRegistry;
import me.flame.communication.data.GroupedDataRegistry;
import me.flame.communication.data.GroupedDataRegistryImpl;

import me.flame.communication.data.MessageDataRegistry;
import net.kyori.adventure.text.minimessage.MiniMessage;

import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class SoundAction implements Action {
    private final DataRegistry dataRegistry;

    public SoundAction(DataRegistry dataRegistry) {
        this.dataRegistry = dataRegistry;
    }

    @Override
    public int expectedArgs() {
        return 3;
    }

    @Override
    public void execute(Player involvedPlayer) {
        Sound configuredSound = dataRegistry.getRegistration("sound");
        Float volume = dataRegistry.getRegistrationOr("volume", 1.0f);
        Float pitch = dataRegistry.getRegistrationOr("pitch", 1.0f);
        involvedPlayer.playSound(involvedPlayer.getLocation(), configuredSound, volume, pitch);
    }
}
