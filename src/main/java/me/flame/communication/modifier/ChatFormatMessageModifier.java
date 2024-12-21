package me.flame.communication.modifier;

import me.clip.placeholderapi.PlaceholderAPI;
import me.flame.communication.EnhancedCommunication;

import me.flame.communication.messages.SerializedMessage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ChatFormatMessageModifier implements MessageModifier {
    @Override
    public ModifierPriority getPriority() {
        return ModifierPriority.LOWEST;
    }

    @Override
    public void modify(final @NotNull SerializedMessage data) {
        Player player = data.getSender();
        SerializedMessage newMessage = EnhancedCommunication.get()
                .getChatManager()
                .getChatFormatManager()
                .getFormat(data, player);
        if (newMessage.isEmpty()) return;

        checkPlaceholders(newMessage, player);
    }

    private static void checkPlaceholders(SerializedMessage data, Player player) {
        if (!EnhancedCommunication.get().getPrimaryConfig().isChatFormatPlaceholderApiEnabled() ||
                !Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            return;
        }

        data.setMessage(PlaceholderAPI.setPlaceholders(player, data.getMessage()));
    }
}
