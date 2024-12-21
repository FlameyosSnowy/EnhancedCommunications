package me.flame.communication.actions.types;

import me.clip.placeholderapi.PlaceholderAPI;
import me.flame.communication.EnhancedCommunication;
import me.flame.communication.actions.Action;
import me.flame.communication.data.MessageDataRegistry;
import me.flame.communication.events.actions.PreActionBarExecuteEvent;
import me.flame.communication.managers.ChatCooldownManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public record ActionBarAction(MessageDataRegistry<String> dataRegistry) implements Action {
    @Override
    public int expectedArgs() {
        return 1;
    }

    @Override
    public void execute(Player involvedPlayer) {
        PreActionBarExecuteEvent event = new PreActionBarExecuteEvent(!Bukkit.isPrimaryThread(), this, this.dataRegistry);
        Bukkit.getPluginManager().callEvent(event);

        if (event.isCancelled()) {
            return;
        }

        ChatCooldownManager chatCooldownManager = EnhancedCommunication.get().getChatManager().getCooldownManager();

        final String formattedMessage = this.checkPlaceholders(this.dataRegistry.getData()
                .replace("%player%", MINI_MESSAGE.serialize(this.dataRegistry.getPlayer().displayName()))
                .replace("%remaining-cooldown%", String.valueOf(chatCooldownManager.getCooldownRemainder(involvedPlayer)))
                .replace("%total-cooldown%", String.valueOf(EnhancedCommunication.get().getPrimaryConfig().getChatCooldown())));
        involvedPlayer.sendActionBar(MINI_MESSAGE.deserialize(formattedMessage));
    }

    private String checkPlaceholders(final @NotNull String formattedMessage) {
        if (this.dataRegistry.getRegistrationsSize() != this.expectedArgs()
                    || Bukkit.getPluginManager().getPlugin("PlaceholderAPI") == null) return formattedMessage;

        String[] words = dataRegistry.getWords();
        if (!words[words.length - 1].equals("papi")) {
            throw new IllegalArgumentException(); // We are not expecting it to not say papi.
        }

        return PlaceholderAPI.setPlaceholders(dataRegistry.getPlayer(), formattedMessage);
    }
}
