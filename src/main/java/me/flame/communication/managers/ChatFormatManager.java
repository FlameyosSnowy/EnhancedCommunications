package me.flame.communication.managers;

import me.flame.communication.providers.ChatProvider;
import me.flame.communication.utils.Reloadable;
import org.bukkit.entity.Player;

public interface ChatFormatManager extends Reloadable {
    String getFormat(String message, Player player);
}
