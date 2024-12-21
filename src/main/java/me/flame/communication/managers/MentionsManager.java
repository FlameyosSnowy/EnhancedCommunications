package me.flame.communication.managers;

import me.flame.communication.data.GroupedDataRegistry;
import me.flame.communication.messages.SerializedMessage;
import org.bukkit.entity.Player;

public interface MentionsManager {
    /**
     * Processes the given message and looks for any player mentions in it.
     * If any are found, the message is modified to include the player's display name instead of their username.
     * The modified message is then returned within a {@link GroupedDataRegistry}.
     *
     * @param player  the player the message is from
     * @param message the message to look for mentions in
     */
    void changeMentionsLook(final Player player, final SerializedMessage message);
}
