package me.flame.communication.managers;

import me.flame.communication.utils.Reloadable;
import org.bukkit.entity.Player;

public interface ChatFormatManager extends Reloadable {
    /**
     * Retrieves the chat format for the specified message and player.
     *
     * <p>This method returns a formatted chat message based on the player's
     * group format configuration. The message may include placeholder values
     * such as the player's name, world, and other metadata.
     *
     * @param message the original chat message to be formatted
     * @param player the player whose chat format is being applied
     * @return the formatted chat message as a String
     */
    String getFormat(String message, Player player);
}
