package me.flame.communication.modifier;

import me.clip.placeholderapi.PlaceholderAPI;
import me.flame.communication.EnhancedCommunication;
import me.flame.communication.data.RawDataRegistry;
import me.flame.communication.providers.ChatProvider;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventPriority;
import org.jetbrains.annotations.NotNull;

public class ChatFormatMessageModifier implements MessageModifier {
    @Override
    public EventPriority getPriority() {
        return EventPriority.HIGHEST;
    }

    @Override
    public String modify(final @NotNull RawDataRegistry dataRegistry) {
        Player player = dataRegistry.getPlayer();
        return checkPlaceholders(EnhancedCommunication.get()
                .getChatManager()
                .getChatFormatManager()
                .getFormat(dataRegistry.getMessage(), player), player);
    }

    private static String checkPlaceholders(String message, Player player) {
        if (!EnhancedCommunication.get().getPrimaryConfig().isChatFormatPlaceholderApiEnabled() ||
                !Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            return message;
        }

        return PlaceholderAPI.setPlaceholders(player, message);
    }
}