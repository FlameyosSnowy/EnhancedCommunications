package me.flame.communication.actions.types;

import me.clip.placeholderapi.PlaceholderAPI;
import me.flame.communication.actions.Action;
import me.flame.communication.data.MessageDataRegistry;
import me.flame.communication.hooks.PlaceholderHook;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ActionBarAction implements Action {
    private final MessageDataRegistry dataRegistry;

    public ActionBarAction(MessageDataRegistry dataRegistry) {
        this.dataRegistry = dataRegistry;
    }

    @Override
    public int expectedArgs() {
        return 1;
    }

    @Override
    public void execute(Player involvedPlayer) {
        if (this.dataRegistry.getRegistrationsSize() < expectedArgs()) {
            throw new IllegalArgumentException();
        }
        String formattedMessage = this.dataRegistry.getMessage().replace("%player%", MINI_MESSAGE.serialize(this.dataRegistry.getPlayer().displayName()));
        formattedMessage = this.checkPlaceholders(formattedMessage);
        involvedPlayer.sendActionBar(MINI_MESSAGE.deserialize(formattedMessage));
    }

    private String checkPlaceholders(final @NotNull String formattedMessage) {
        if (this.dataRegistry.getRegistrationsSize() - 1 == this.expectedArgs()
                    || Bukkit.getPluginManager().getPlugin("PlaceholderAPI") == null) return formattedMessage;

        String[] words = dataRegistry.getWords();
        if (!words[words.length - 1].equals("papi")) {
            throw new IllegalArgumentException(); // We are not expecting it to not say papi.
        }

        return PlaceholderAPI.setPlaceholders(dataRegistry.getPlayer(), formattedMessage);
    }
}
